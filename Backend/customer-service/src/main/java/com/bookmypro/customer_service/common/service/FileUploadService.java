package com.bookmypro.customer_service.common.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.bookmypro.customer_service.config.HttpClientConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {

	private final HttpClientConfig httpClientConfig;

	@Value("${file.storage.path}")
	private String fileStoragePath;

	FileUploadService(HttpClientConfig httpClientConfig) {
		this.httpClientConfig = httpClientConfig;
	}

	public List<String> uploadFile(List<MultipartFile> files) {
		List<String> uploadedFiles = new ArrayList<>();

		try {
			Path uplodPath = Paths.get(fileStoragePath);

			if (!Files.exists(uplodPath)) {
				Files.createDirectories(uplodPath);
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

				String fileName = System.currentTimeMillis() + extension;

				Path filePath = uplodPath.resolve(fileName);

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
