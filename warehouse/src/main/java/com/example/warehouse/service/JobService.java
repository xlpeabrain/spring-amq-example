package com.example.warehouse.service;

import com.example.warehouse.entity.Job;
import com.example.warehouse.exceptions.JobNotFoundException;
import com.example.warehouse.repository.JobRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JobService {

    private JobRepository jobRepository;
    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    protected JobService(){}

    @Autowired
    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Transactional()
    public void claimJob(String jobId, String droneId) {
        Job job = jobRepository.findFirstByRemarksContainingAndAssigneeIsNull(jobId);
        if (null == job) {
            throw new JobNotFoundException("Job not found for jobId: " + jobId + ", droneId: " + droneId);
        } else {
            job.setAssignee(droneId);
            logger.info("Assigned jobs {} to DroneId: {}", job, droneId);
        }
    }

}
