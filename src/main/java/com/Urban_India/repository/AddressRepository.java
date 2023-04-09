package com.Urban_India.repository;

import com.Urban_India.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.nio.file.LinkOption;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
