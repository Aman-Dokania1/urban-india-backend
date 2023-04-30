package com.Urban_India.controller;

import com.Urban_India.entity.Status;
import com.Urban_India.service.StatusService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/status")
@AllArgsConstructor
public class StatusController {

    private StatusService statusService;

    @PostMapping
    public ResponseEntity<String> crateStatus(@RequestBody Status status){
        statusService.createStatus(status);
        return  new ResponseEntity<>("status created successfully", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Status>> getAllStatus(){
        return ResponseEntity.ok(statusService.getAllStatus());
    }
}
