package com.example.asyncdemo.event;

import com.example.asyncdemo.job.JobEntity;
import org.springframework.context.ApplicationEvent;

public class AsyncDemoEvent extends ApplicationEvent {

    private Integer jobId;

    public AsyncDemoEvent(JobEntity source) {
        super(source);
        this.jobId = source.getId();
    }

    public Integer getJobId() {
        return this.jobId;
    }
}
