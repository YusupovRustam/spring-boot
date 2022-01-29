package com.company.repository;

import com.company.FileStorageStatus;
import com.company.entity.FileStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileStorageRepository extends JpaRepository<FileStorage,Long> {
    FileStorage findByHashId(String hashId);

    List<FileStorage>findAllByFileStorageStatus(FileStorageStatus fileStorageStatus);
}
