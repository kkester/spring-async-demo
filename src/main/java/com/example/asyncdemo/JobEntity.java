package com.example.asyncdemo;

import java.util.Date;

public class JobEntity {

    private Integer id;
    private String status;
    private Date created;
    private Date updated;

    public JobEntity() {
        this.created = new Date();
        this.updated = new Date();
    }

    public Date getCreated() {
        return created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
