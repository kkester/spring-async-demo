package com.example.asyncdemo;

import lombok.Data;

import java.util.Date;

@Data
public class JobEntity {

    private Integer id;
    private String status;
    private Date created;
    private Date updated;

    public JobEntity() {
        this.created = new Date();
        this.updated = new Date();
    }

}
