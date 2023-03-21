package com.Urban_India.service.impl;

import com.Urban_India.entity.Image;
import com.Urban_India.entity.Role;
import com.Urban_India.entity.User;
import com.Urban_India.exception.UrbanApiException;
import com.Urban_India.payload.LoginDto;
import com.Urban_India.payload.RegisterDto;
import com.Urban_India.repository.ImageRepositroy;
import com.Urban_India.repository.RoleRepository;
import com.Urban_India.repository.UserRepository;
import com.Urban_India.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private RoleRepository roleRepository;
    private UserRepository userRepository;

    private ImageRepositroy imageRepositroy;
    private PasswordEncoder passwordEncoder;
    @Override
    public String login(LoginDto loginDto) {
        return null;
    }

    @Override
    public String register(RegisterDto registerDto) {
        System.out.println(registerDto.getEmail()+" "+registerDto.getEmail()+" "+registerDto.getImages());
        // check if user is exists by username
        if(userRepository.existsByUsername(registerDto.getUsername()))
            throw new UrbanApiException(HttpStatus.BAD_REQUEST,"Username is already exist");
        if(userRepository.existsByEmail((registerDto.getEmail())))
            throw new UrbanApiException(HttpStatus.BAD_REQUEST,"user email is already exist");

        User user=new User();
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setUsername(registerDto.getUsername());

//        Image image=new Image();
//        image.setImage_link(registerDto.getImages());
//
//        imageRepositroy.save(image);

        user.setImage(registerDto.getImages());
        System.out.println(user.getUsername()+" "+user.getEmail()+" "+user.getImage());
        Set<Role> roles=new HashSet<>();

        Role userRole=roleRepository.findByName("ROLE_USER").orElseThrow(()->{
            System.out.println("error");
            return  new UrbanApiException(HttpStatus.BAD_REQUEST, "ERROR");
        });

        System.out.println("123456787654323456765432userRole.toString()");
        System.out.println(userRole.toString());
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);
        return "user registered successfully";
    }
}

