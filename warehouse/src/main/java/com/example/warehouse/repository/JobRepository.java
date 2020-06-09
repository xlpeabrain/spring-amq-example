package com.example.warehouse.repository;

import com.example.warehouse.entity.Job;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JobRepository extends CrudRepository<Job, Long> {
    List<Job> findByRemarksContainingAndAssigneeIsNull(String remarks);
    Job findById(long id);
}
