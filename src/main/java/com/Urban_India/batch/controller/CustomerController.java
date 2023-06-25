package com.Urban_India.batch.controller;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("/job/batch")
public class CustomerController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    private String temp_storage = "/home/aman/Downloads/batch-file/";

    @PostMapping("/importCustomers")
    public void importCsvToDBJob(@RequestParam("file")MultipartFile multipartFile) throws IOException {

        String fileName =  multipartFile.getOriginalFilename();
        File file = new File(temp_storage+fileName);
        multipartFile.transferTo(file);

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("fullPathFileName",temp_storage+fileName)
                .addLong("startAt", System.currentTimeMillis()).toJobParameters();
        try {
            JobExecution jobExecution =jobLauncher.run(job, jobParameters);
            if(jobExecution.getExitStatus().getExitCode().equals(ExitStatus.COMPLETED)){
                Files.deleteIfExists(Paths.get(temp_storage+fileName));
            }

        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException |
                 JobParametersInvalidException e) {
            e.printStackTrace();
        }
    }
}
