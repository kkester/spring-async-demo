package com.example.asyncdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class AsyncController {

    @Autowired
    private AsyncDemoService asyncDemoService;

    @GetMapping(value = "/jobs")
    public ResponseEntity<Collection<JobEntity>> getJobs() {
        return ResponseEntity.ok(this.asyncDemoService.getJobs());
    }

    @PostMapping(value = "/jobs")
    public ResponseEntity<JobEntity> createJob() {

        System.out.println("Starting on" + Thread.currentThread().getName());

        JobEntity job = this.asyncDemoService.createJob();

        System.out.println("Completing on" + Thread.currentThread().getName());

        return ResponseEntity.ok(job);
    }

    @PostMapping(value = "/jobs/{id}")
    public ResponseEntity<Void> processJob(@PathVariable Integer id, @RequestBody(required = false) JobEntity jobData) {

        System.out.println("Starting on" + Thread.currentThread().getName());

        if (jobData == null) {
            this.asyncDemoService.processJob(id);
        } else {
            this.asyncDemoService.editJob(id, jobData);
        }

        System.out.println("Completing on" + Thread.currentThread().getName());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
