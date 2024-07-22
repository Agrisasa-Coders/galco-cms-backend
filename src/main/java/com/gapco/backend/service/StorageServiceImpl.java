package com.gapco.backend.service;

import com.gapco.backend.exception.InternalServerErrorException;
import com.gapco.backend.util.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Date;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class StorageServiceImpl implements StorageService{

    private final LogService logService;

    @Value("${upload.directory}")
    private String uploadDirectory;

    @Override
    public String storeFileToFileSystem(MultipartFile file, String imageName) {


        Path newFile = Paths.get(uploadDirectory + new Date().getTime() + "-" + imageName);

        try{

            Files.createDirectories(newFile.getParent());
            Files.write(newFile,file.getBytes());

            // Set file permissions to 644
            Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rw-r--r--");
            Files.setPosixFilePermissions(newFile, perms);

        } catch(Exception e){

            logService.logToFile(AppConstants.LOGS_PATH,"StoreToFileSystemException",e.getMessage());
            throw new InternalServerErrorException("There is an error in uploading a file");
        }

        return newFile.toAbsolutePath()
                .toString();
    }
}
