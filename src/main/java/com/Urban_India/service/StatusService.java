package com.Urban_India.service;

import com.Urban_India.entity.Status;

import java.util.List;

public interface StatusService {
    public void createStatus(Status status);

    public List<Status> getAllStatus();
}
