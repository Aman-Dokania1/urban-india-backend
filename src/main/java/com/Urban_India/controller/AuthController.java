package com.Urban_India.controller;

import com.Urban_India.entity.Image;
import com.Urban_India.payload.RegisterDto;
import com.Urban_India.repository.ImageRepositroy;
import com.Urban_India.service.AuthService;
import com.Urban_India.util.ImageUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AuthController {

    private ObjectMapper mapper;
    private AuthService authService;
    private ImageRepositroy imageRepositroy;

    @PostMapping(value = {"auth/signup" , "auth/register"})
    public ResponseEntity<String> signUp(@Valid @RequestParam("data") String data,
                                         @RequestParam("file") MultipartFile file) throws IOException
    {

//        System.out.println(file);
//        File file1=new ClassPathResource("static/").getFile();
//        System.out.println(file.getOriginalFilename());
//        // File path
//        File saveFile= new ClassPathResource("static").getFile();
//
//        Path path= Paths.get(saveFile.getAbsolutePath()+File.separator+System.currentTimeMillis()+file.getOriginalFilename());
//
//
//        String fileName =  (  "" + System.currentTimeMillis() + file.getOriginalFilename() );
//        // Save file
//        Files.copy(file.getInputStream(), path,
//                StandardCopyOption.REPLACE_EXISTING);
//
//        // get file uri
//        String uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/images/")
//                .path(fileName).toUriString();

        Image image =imageRepositroy.save(Image.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtil.compressImage(file.getBytes())).build());

        // converting string to json
        RegisterDto registerDto = mapper.readValue(data, RegisterDto.class);
        registerDto.setImages(image);

        String response = authService.register(registerDto);
        return ResponseEntity.ok(response);
    }
}
