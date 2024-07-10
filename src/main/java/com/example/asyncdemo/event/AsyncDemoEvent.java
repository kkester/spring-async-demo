package com.example.asyncdemo.event;

import com.example.asyncdemo.job.JobEntity;
import org.springframework.context.ApplicationEvent;

public class AsyncDemoEvent extends ApplicationEvent {

    public AsyncDemoEvent(JobEntity source) {
        super(source);
    }

    public Integer getJobId() {
        return ((JobEntity)getSource()).getId();
    }
}
