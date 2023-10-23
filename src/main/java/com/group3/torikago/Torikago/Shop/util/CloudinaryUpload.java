package com.group3.torikago.Torikago.Shop.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudinaryUpload {
    String uploadFile(MultipartFile multipartFile) throws IOException;
}
