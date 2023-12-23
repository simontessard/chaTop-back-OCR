package com.chatop.api.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

import com.chatop.api.services.AmazonService;

@RestController
public class FileUploadController {

    private final AmazonService amazonService;

    public FileUploadController(AmazonService amazonService) {
        this.amazonService = amazonService;
    }

    @PostMapping("api/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        String key = file.getOriginalFilename();
        try {
            File tempFile = File.createTempFile("upload-", "");
            file.transferTo(tempFile);
            String url = amazonService.putObject(key, tempFile.getAbsolutePath());
            return "File uploaded successfully. URL: " + url;
        } catch (Exception e) {
            return "Failed to upload file: " + e.getMessage();
        }
    }
}
