package com.example.warehouse.repository;

import com.example.warehouse.entity.Job;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;


import javax.persistence.LockModeType;
import java.util.List;

public interface JobRepository extends CrudRepository<Job, Long> {
    List<Job> findByRemarksContainingAndAssigneeIsNull(String remarks);
    @Lock(LockModeType.PESSIMISTIC_READ)
    Job findFirstByRemarksContainingAndAssigneeIsNull(String remarks);
    Job findById(long id);
}
