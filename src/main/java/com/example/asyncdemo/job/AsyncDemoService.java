package com.example.asyncdemo.job;

import com.example.asyncdemo.event.AsyncDemoEvent;
import com.example.asyncdemo.stream.AsyncDemoStreamProducer;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class AsyncDemoService {

    private final ApplicationEventMulticaster applicationEventMulticaster;
    private final JobEntityRepository jobRepository;
    private final AsyncDemoStreamProducer producer;

    public JobEntity createJob() {

        log.info("Creating Job");

        JobEntity job = new JobEntity();
        job.setStatus(JobStatus.NEW);
        jobRepository.save(job);
        applicationEventMulticaster.multicastEvent(new AsyncDemoEvent(job));

        log.info("Completed Creating job {}", job);

        return job;
    }

    public void editJob(Integer id, JobEntity jobData) {

        log.info("Starting Update for job {}", id);

        JobEntity job = this.jobRepository.get(id);
        job.applyStatus(jobData.getStatus());
        jobRepository.save(job);
        producer.sendMessage(job);

        log.info("Completing Update for {}", job);
    }

    public Collection<JobEntity> getJobs() {
        return this.jobRepository.getJobs();
    }

    @SneakyThrows
    public void processJob(Integer jobId) {

        JobEntity job = this.jobRepository.get(jobId);
        boolean failed = JobStatus.NEW.equals(job.getStatus());
        job.applyStatus(JobStatus.PROCESSING);
        for (int i = 1; i < 11; i++) {
            log.info("Processing Job in {} Status on Iteration {}. VT: {}", job.getStatus(), i, Thread.currentThread().isVirtual());
            Thread.sleep(1);
        }
        job.applyStatus(failed ? JobStatus.FAILED : JobStatus.DONE);
    }

    public void retryFailedJobs() {
        Collection<JobEntity> jobs = jobRepository.getJobsByStatus(JobStatus.FAILED);
        jobs.forEach(j -> this.processJob(j.getId()));
    }
}
