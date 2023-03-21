package com.Urban_India.repository;

import com.Urban_India.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String name);
    Optional<User> findByUsernameOrEmail(String name,String email);

    Boolean existsByUsername(String name);
    Boolean existsByEmail(String email);
}
