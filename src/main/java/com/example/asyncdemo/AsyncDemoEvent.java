package com.example.asyncdemo;

import org.springframework.context.ApplicationEvent;

public class AsyncDemoEvent extends ApplicationEvent {

    public AsyncDemoEvent(JobEntity source) {
        super(source);
    }

    public Integer getJobId() {
        return ((JobEntity)getSource()).getId();
    }
}
