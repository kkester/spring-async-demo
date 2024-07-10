package com.example.asyncdemo.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AsyncController {

    private final AsyncDemoService asyncDemoService;

    @GetMapping(value = "/jobs")
    public ResponseEntity<Collection<JobEntity>> getJobs() {
        return ResponseEntity.ok(this.asyncDemoService.getJobs());
    }

    @PostMapping(value = "/jobs")
    public ResponseEntity<JobEntity> createJob() {

        log.info("Starting job creation");

        JobEntity job = this.asyncDemoService.createJob();

        log.info("Completed job creation for {}", job);

        return ResponseEntity.ok(job);
    }

    @PostMapping(value = "/jobs/{id}")
    public ResponseEntity<Void> processJob(@PathVariable Integer id, @RequestBody(required = false) JobEntity jobData) {

        log.info("Starting Job Update");

        if (jobData == null) {
            this.asyncDemoService.processJob(id);
        } else {
            this.asyncDemoService.editJob(id, jobData);
        }

        log.info("Completed Job Update");

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
