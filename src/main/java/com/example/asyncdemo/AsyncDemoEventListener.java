package com.example.asyncdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class AsyncDemoEventListener implements ApplicationListener<AsyncDemoEvent> {

    @Autowired
    private AsyncDemoService asyncDemoService;

    @Override
    public void onApplicationEvent(AsyncDemoEvent event) {
        asyncDemoService.processJob(event.getJobId());
    }
}