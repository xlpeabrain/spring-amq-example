package com.example.warehouse.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Long id;
    @Getter
    private String remarks;
    @Getter
    private String requestDateTime;
    @Getter @Setter
    private String assignee;

    protected Job() {}

    public Job(String remarks, String requestDateTime) {
        this.remarks = remarks;
        this.requestDateTime = requestDateTime;
    }


}
