package com.Urban_India.repository;

import com.Urban_India.entity.ServiceProviderEntitiy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<ServiceProviderEntitiy,Long> {
}
