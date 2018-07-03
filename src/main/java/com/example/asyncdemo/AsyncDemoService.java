package com.example.asyncdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.messaging.MessageHandlingException;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class AsyncDemoService {

    @Autowired
    @Qualifier("applicationEventMulticaster")
    private ApplicationEventMulticaster applicationEventPublisher;

    @Autowired
    private JobEntityRepository jobRepository;

    @Autowired
    private AsyncDemoStreamProducer producer;

    public JobEntity createJob() {

        System.out.println("Starting on " + Thread.currentThread().getName());

        JobEntity job = new JobEntity();
        job.setStatus("New");
        jobRepository.save(job);
        producer.sendMessage(job);

        System.out.println("Completing " + Thread.currentThread().getName());

        return job;
    }

    public void editJob(Integer id, JobEntity jobData) {

        System.out.println("Starting on " + Thread.currentThread().getName());

        JobEntity job = this.jobRepository.get(id);
        job.setStatus(jobData.getStatus());
        job.setUpdated(jobData.getUpdated());
        jobRepository.save(job);
        applicationEventPublisher.multicastEvent(new AsyncDemoEvent(job));

        System.out.println("Completing " + Thread.currentThread().getName());
    }

    public Collection<JobEntity> getJobs() {
        return this.jobRepository.getJobs();
    }


    @Async
    public void processJob(Integer jobId) {

        JobEntity job = this.jobRepository.get(jobId);
        job.setStatus("Processing");
        for (int i = 1; i < 11; i++) {
            System.out.println("Processing Job Started at :" + new Date() + " Iteration " + i + " on " + Thread.currentThread().getName());
            try {
                Thread.sleep(2000L);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            job.setUpdated(new Date());
        }
        job.setStatus("Done");
    }

    public void retryFailedJobs() {
        Collection<JobEntity> jobs = jobRepository.getJobsByStatus("failed");
        jobs.forEach(j -> this.processJob(j.getId()));
    }
}
