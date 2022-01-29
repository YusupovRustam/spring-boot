package com.company.resourse;

import com.company.entity.FileStorage;
import com.company.servic.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileUrlResource;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.net.URLEncoder;

@RestController
@RequestMapping("/fileStorage")
public class FileStorageResourse {

    @Value("${upload.folder}")
    private String uploadFolder;

    @Autowired
    FileStorageService fileStorageService;



    @PostMapping
    public ResponseEntity save(@RequestParam("file")MultipartFile multipartFile){
        fileStorageService.save(multipartFile);
        return ResponseEntity.ok(multipartFile.getName()+" file saqlandi");
    }

    @GetMapping("/{hashId}")
    public ResponseEntity previewFile(@PathVariable(value = "hashId") String hashId) throws IOException {
        FileStorage fileStorage=fileStorageService.findByHashId(hashId);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"inline; fileName\""+
                URLEncoder.encode(fileStorage.getName())).contentType(MediaType.parseMediaType(fileStorage.getContentType()))
                .contentLength(fileStorage.getFileSize()).
                body(new FileUrlResource(String.format("%s/%s",uploadFolder,fileStorage.getUploadPath())));
    }

    @GetMapping("/download/{hashId}")
    public ResponseEntity downloadFile(@PathVariable(value = "hashId") String hashId) throws IOException {
        FileStorage fileStorage=fileStorageService.findByHashId(hashId);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"attachment; fileName\""+
                        URLEncoder.encode(fileStorage.getName())).contentType(MediaType.parseMediaType(fileStorage.getContentType()))
                .contentLength(fileStorage.getFileSize()).
                body(new FileUrlResource(String.format("%s/%s",uploadFolder,fileStorage.getUploadPath())));
    }


    @DeleteMapping("/{hashId}")
    public ResponseEntity deleteFileStorage(@PathVariable String hashId){
        fileStorageService.delete(hashId);
        return ResponseEntity.ok("Fayl ochirildi");
    }
}
