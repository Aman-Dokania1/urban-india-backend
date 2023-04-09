package com.Urban_India.service.impl;

import com.Urban_India.entity.Status;
import com.Urban_India.repository.StatusRepository;
import com.Urban_India.service.StatusService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StatusServiceImpl implements StatusService {

    private StatusRepository statusRepository;
    @Override
    public void createStatus(Status status) {
        statusRepository.save(status);
    }
}
