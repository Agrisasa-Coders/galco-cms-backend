package com.gapco.backend.service;


import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    String storeFileToFileSystem(MultipartFile file, String imageName);
}
