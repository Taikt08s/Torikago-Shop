package com.group3.torikago.Torikago.Shop.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploadUtil {
    public static void saveFile(String uploadDir, MultipartFile multipartFile, String fileName) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            System.out.println(filePath.toFile().getAbsolutePath());
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException("Couldn't save the uploaded file:" + fileName);
        }
    }
    
    public static void resetDirectory(String uploadDir) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        
        if (Files.isDirectory(uploadPath)) {
            try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(uploadPath)) {
                for (Path entry : directoryStream) {
                        Files.delete(entry);                  
                }
            }
            Files.delete(uploadPath);
        }
    }
}
