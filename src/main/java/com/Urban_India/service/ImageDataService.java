package com.Urban_India.service;

import com.Urban_India.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageDataService {
    public byte[] getImage(String name);

    public Image saveImage(MultipartFile file) throws IOException;
}
