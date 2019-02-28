package com.example.asyncdemo;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class AsyncDemoService {

    @Autowired
    @Qualifier("applicationEventMulticaster")
    private ApplicationEventMulticaster applicationEventPublisher;

    @Autowired
    private JobEntityRepository jobRepository;

    @Autowired
    private AsyncDemoStreamProducer producer;

    public JobEntity createJob() {

        log.info("Starting on " + Thread.currentThread().getName());

        JobEntity job = new JobEntity();
        job.setStatus("New");
        jobRepository.save(job);
        producer.sendMessage(job);

        log.info("Completing " + Thread.currentThread().getName());

        return job;
    }

    public void editJob(Integer id, JobEntity jobData) {

        log.info("Starting on " + Thread.currentThread().getName());

        JobEntity job = this.jobRepository.get(id);
        job.setStatus(jobData.getStatus());
        job.setUpdated(jobData.getUpdated());
        jobRepository.save(job);
        applicationEventPublisher.multicastEvent(new AsyncDemoEvent(job));

        log.info("Completing " + Thread.currentThread().getName());
    }

    public Collection<JobEntity> getJobs() {
        return this.jobRepository.getJobs();
    }


    @Async
    public void processJob(Integer jobId) {

        JobEntity job = this.jobRepository.get(jobId);
        boolean failed = "new".equalsIgnoreCase(job.getStatus());
        for (int i = 1; i < 11; i++) {
            log.info("Processing Job in {} Status Started at : {} Iteration {} on {}", job.getStatus(), new Date(), i, Thread.currentThread().getName());
            job.setStatus("Processing");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                log.error("Error Processing Job", e);
            }
            job.setUpdated(new Date());
        }
        job.setStatus(failed ? "Failed" : "Done");
    }

    @Async
    public void retryFailedJobs() {
        Collection<JobEntity> jobs = jobRepository.getJobsByStatus("Failed");
        jobs.forEach(j -> this.processJob(j.getId()));
    }
}
