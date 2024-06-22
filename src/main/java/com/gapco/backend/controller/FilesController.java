package com.gapco.backend.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("api/v1/files")
public class FilesController {
    @Value("${document.upload-directory}")
    String uploadDirectory;
    @GetMapping("")
    public ResponseEntity<Resource> downloadFile(@RequestParam(name = "filename") String fileName) throws IOException {
        // Load the file as a resource
        Path filePath = Paths.get(fileName);
        Resource resource = new org.springframework.core.io.UrlResource(filePath.toUri());

        // Set the content type for the response
        MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;
        if (Files.probeContentType(filePath) != null) {
            mediaType = MediaType.parseMediaType(Files.probeContentType(filePath));
        }

        // Prepare the response with appropriate headers
        return ResponseEntity.ok()
                .contentType(mediaType)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
