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
import com.Urban_India.security.JwtTokenProvider;
import com.Urban_India.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;

    private JwtTokenProvider jwtTokenProvider;
    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUserNameOrEmail(),loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token=jwtTokenProvider.generateToken(authentication);
        return token;

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
        user.setGender(registerDto.getGender());
        user.setPhone_number((registerDto.getPhone_number()));

        user.setImage(registerDto.getImages());
        System.out.println(user.getUsername()+" "+user.getEmail()+" "+user.getImage());
        Set<Role> roles=new HashSet<>();

        Role userRole=roleRepository.findByName("ROLE_USER").orElseThrow(()->{
            System.out.println("error");
            return  new UrbanApiException(HttpStatus.BAD_REQUEST, "ERROR");
        });

        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);
        return "user registered successfully";
    }
}

