package com.Urban_India.controller;

import com.Urban_India.entity.Image;
import com.Urban_India.payload.JwtAuthResponse;
import com.Urban_India.payload.LoginDto;
import com.Urban_India.payload.RegisterDto;
import com.Urban_India.repository.ImageRepositroy;
import com.Urban_India.service.AuthService;
import com.Urban_India.service.ImageDataService;
import com.Urban_India.util.ImageUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;


@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AuthController {

    private ObjectMapper mapper;
    private AuthService authService;
    private ImageDataService imageDataService;

    @PostMapping(value = {"auth/signup" , "auth/register"})
    public ResponseEntity<String> signUp(@Valid @RequestParam("data") String data, @RequestParam("file") MultipartFile file) throws IOException
    {

        Image image =imageDataService.saveImage(file);

        // converting string to json
        RegisterDto registerDto = mapper.readValue(data, RegisterDto.class);
        registerDto.setImages(image);

        String response = authService.register(registerDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = {"auth/login","auth/signin"})
    private ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto){
        System.out.println("called>>>>>>>>>>>");
        String token=authService.login(loginDto);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
    }
}
