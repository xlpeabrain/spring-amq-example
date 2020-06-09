package com.example.warehouse.controller;

import com.example.warehouse.entity.Job;
import com.example.warehouse.exceptions.JobNotFoundException;
import com.example.warehouse.repository.JobRepository;
import com.example.warehouse.request.ClaimRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@RestController
public class JobController {

    private JobRepository jobRepository;
    private Logger logger = LoggerFactory.getLogger(JobController.class.getName());
    protected JobController(){};

    @Autowired
    public JobController(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @GetMapping(path = "/job", produces = MediaType.APPLICATION_JSON_VALUE)
    public String createJob(){
        String id = UUID.randomUUID().toString();
        Job job = new Job("This is new job: " + id, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        jobRepository.save(job);
        return id;
    }

    @PostMapping(path = "/jobClaim", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void claimJob(@RequestBody ClaimRequest claimRequest) {
        List<Job> jobList = jobRepository.findByRemarksContainingAndAssigneeIsNull(claimRequest.getJobId());
        if (jobList.size() < 1) {
            throw new JobNotFoundException("Job not found for " + claimRequest.toString());
        } else {
            Job job = jobList.get(0);
            job.setAssignee(claimRequest.getDroneId());
            jobRepository.save(job);
            logger.info("Job:{} found, Assigned drone: {}", job.getId(), job.getAssignee());
        }

    }
}
