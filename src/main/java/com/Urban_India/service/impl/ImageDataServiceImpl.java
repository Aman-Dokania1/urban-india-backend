package com.Urban_India.service.impl;


import com.Urban_India.entity.Image;
import com.Urban_India.repository.ImageRepositroy;
import com.Urban_India.service.ImageDataService;
import com.Urban_India.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImageDataServiceImpl implements ImageDataService {

    @Autowired
    private ImageRepositroy imageDataRepository;

//    public ImageUploadResponse uploadImage(MultipartFile file) throws IOException {
//
//        imageDataRepository.save(ImageData.builder()
//                .name(file.getOriginalFilename())
//                .type(file.getContentType())
//                .imageData(ImageUtil.compressImage(file.getBytes())).build());
//
//        return new ImageUploadResponse("Image uploaded successfully: " +
//                file.getOriginalFilename());
//
//    }

//    @Transactional
//    public ImageData getInfoByImageByName(String name) {
//        Optional<ImageData> dbImage = imageDataRepository.findByName(name);
//
//        return ImageData.builder()
//                .name(dbImage.get().getName())
//                .type(dbImage.get().getType())
//                .imageData(ImageUtil.decompressImage(dbImage.get().getImageData())).build();
//
//    }

    public byte[] getImage(String name) {
        Optional<Image> dbImage = imageDataRepository.findByName(name);
        byte[] image = ImageUtil.decompressImage(dbImage.get().getImageData());
        return image;
    }

    @Override
    public Image saveImage(MultipartFile file) throws IOException {
        Image image =imageDataRepository.save(Image.builder()
                .name(System.currentTimeMillis()+file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtil.compressImage(file.getBytes())).build());
        return image;
    }



}