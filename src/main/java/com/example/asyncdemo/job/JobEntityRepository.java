package com.example.asyncdemo.job;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class JobEntityRepository {

    private Map<Integer, JobEntity> jobs = new HashMap<>();

    public JobEntity get(Integer id) {
        return jobs.get(id);
    }

    public Collection<JobEntity> getJobs() {
        return jobs.values();
    }

    public Collection<JobEntity> getJobsByStatus(JobStatus status) {
        return this.getJobs().stream()
            .filter(j -> status.equals(j.getStatus()))
            .toList();
    }

    public JobEntity save(JobEntity job) {
        if (job.getId() == null) {
            job.setId(jobs.size() + 1);
        }
        jobs.put(job.getId(), job);
        return job;
    }
}
