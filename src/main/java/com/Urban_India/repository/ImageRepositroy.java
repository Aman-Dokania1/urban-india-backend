package com.Urban_India.repository;

import com.Urban_India.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepositroy extends JpaRepository<Image,Long> {
    Optional<Image> findByName(String name);

}
