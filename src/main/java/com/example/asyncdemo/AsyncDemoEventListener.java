package com.example.asyncdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AsyncDemoEventListener implements ApplicationListener<AsyncDemoEvent> {

    @Autowired
    private AsyncDemoService asyncDemoService;

    @Override
    public void onApplicationEvent(AsyncDemoEvent event) {
        log.info("Triggering Application Event for Job {}", event.getJobId());
        asyncDemoService.processJob(event.getJobId());
    }
}