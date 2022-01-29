package com.company.servic;

import com.company.FileStorageStatus;
import com.company.entity.FileStorage;
import com.company.repository.FileStorageRepository;
import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class FileStorageService {
    @Value("${upload.folder}")
    private String uploadFolder;
    @Autowired
    private FileStorageRepository fileStorageRepository;
    private Hashids hashids;

    public FileStorageService() {
        this.hashids = new Hashids(getClass().getName(), 6);
    }

    public void save(MultipartFile multipartFile) {
        FileStorage fileStorage = new FileStorage();
        fileStorage.setName(multipartFile.getOriginalFilename());
        fileStorage.setExtansion(getExt(multipartFile.getOriginalFilename()));
        fileStorage.setFileSize(multipartFile.getSize());
        fileStorage.setContentType(multipartFile.getContentType());
        fileStorage.setFileStorageStatus(FileStorageStatus.DRAFT);

        fileStorageRepository.save(fileStorage);


        LocalDate localDate = LocalDate.now();
        File uploadFolder1 = new File(String.format("%s/upload_files/%d/%d/%d", this.uploadFolder, localDate.getYear(),
                localDate.getMonthValue(), localDate.getDayOfMonth()));
        if (!uploadFolder1.exists()) {
            uploadFolder1.mkdirs();
            System.out.println("yaratildi");
        }

        fileStorage.setHashId(hashids.encode(fileStorage.getId()));
        fileStorage.setUploadPath(String.format("upload_files/%d/%d/%d/%s.%s", localDate.getYear(),
                localDate.getMonthValue(), localDate.getDayOfMonth(), fileStorage.getHashId(), fileStorage.getExtansion()));

        fileStorageRepository.save(fileStorage);
        File file1 = uploadFolder1.getAbsoluteFile();

      //  System.out.println(file1.exists());

        File file = new File(file1, String.format("%s.%s", fileStorage.getHashId(), fileStorage.getExtansion()));
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public String getExt(String fileName) {
        if (fileName != null && !fileName.isEmpty()) {
            String replace = fileName.replace(".", ",");
            String[] split = replace.split(",");
            return split[1];
        }
        return null;
    }
    @Transactional(readOnly = true)
    public FileStorage findByHashId(String hashId){
        return fileStorageRepository.findByHashId(hashId);
    }

    public void delete(String hashId){
        FileStorage fileStorage=fileStorageRepository.findByHashId(hashId);
        File file=new File(String.format("%s/%s",uploadFolder,fileStorage.getUploadPath()));
        if (file.delete()){
            fileStorageRepository.delete(fileStorage);
        }

    }
        @Scheduled(cron = "0 0 0 * * *")
    public void deleteAllDreft(){
            List<FileStorage>fileStorageList=fileStorageRepository.findAllByFileStorageStatus(FileStorageStatus.DRAFT);
                  for (FileStorage f:fileStorageList){
                      delete(f.getHashId());
                  }
                  fileStorageList.forEach(f -> {delete(f.getHashId());});
    }
}
