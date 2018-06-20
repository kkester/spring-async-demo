package com.example.asyncdemo;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JobEntityRepository {

    private Map<Integer,JobEntity> jobs = new HashMap<>();

    public JobEntity get(Integer id) {
        return jobs.get(id);
    }

    public Collection<JobEntity> getJobs() {
        return jobs.values();
    }

    public Collection<JobEntity> getJobsByStatus(String status) {
        return this.getJobs().stream()
                .filter(j -> status.equalsIgnoreCase(j.getStatus()))
                .collect(Collectors.toList());
    }

    public JobEntity save(JobEntity job) {
        job.setId(jobs.size()+1);
        jobs.put(job.getId(), job);
        return job;
    }
}
