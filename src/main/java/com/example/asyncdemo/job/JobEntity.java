package com.example.asyncdemo.job;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
public class JobEntity {

    private Integer id;
    private JobStatus status;
    private LocalDateTime created;
    private LocalDateTime updated;
    private List<String> audits = new ArrayList<>();

    public JobEntity() {
        this.created = LocalDateTime.now();
        this.updated = LocalDateTime.now();
    }

    public void applyStatus(JobStatus newStatus) {
        updated = LocalDateTime.now();
        audits.add(String.format("Status changed from %s to %s at %s", status, newStatus, updated.format(DateTimeFormatter.ISO_DATE_TIME)));
        status = newStatus;
    }
}
