package com.Urban_India.repository;

import com.Urban_India.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

import java.nio.file.LinkOption;

public interface AddressRepository extends RevisionRepository<Address, Long,Long>, JpaRepository<Address, Long> {
}
