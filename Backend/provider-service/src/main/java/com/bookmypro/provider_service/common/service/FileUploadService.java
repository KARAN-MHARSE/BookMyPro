package com.bookmypro.provider_service.common.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {

    @Value("${file.storage.path}")
    private String fileStoragePath;

    public List<String> uploadFile(List<MultipartFile> files) {
        List<String> uploadedFiles = new ArrayList<>();
        try {
            Path uploadPath = Paths.get(fileStoragePath);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            for (MultipartFile file : files) {
                if (file.isEmpty()) {
                    continue;
                }
                String originalFileName = file.getOriginalFilename();
                String extension = "";
                if (originalFileName != null && originalFileName.contains(".")) {
                    extension = originalFileName.substring(originalFileName.lastIndexOf("."));
                }
                String fileName = System.currentTimeMillis() + "_" + Math.round(Math.random() * 1000) + extension;
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(file.getInputStream(), filePath);
                uploadedFiles.add(filePath.toString());
            }
        } catch (Exception e) {
            throw new RuntimeException("File upload failed", e);
        }
        return uploadedFiles;
    }

    public byte[] getFile(String filePath) {
        try {
            Path path = Paths.get(filePath);
            if (!Files.exists(path)) {
                throw new RuntimeException("File not found");
            }
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new RuntimeException("Unable to read image", e);
        }
    }
}
