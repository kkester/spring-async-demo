package com.example.asyncdemo.event;

import com.example.asyncdemo.job.AsyncDemoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AsyncDemoEventListener {

    private final AsyncDemoService asyncDemoService;

    @EventListener
    public void onApplicationEvent(AsyncDemoEvent event) {
        log.info("Processing Application Event for Job {}. VT: {}", event.getJobId(), Thread.currentThread().isVirtual());
        asyncDemoService.processJob(event.getJobId());
    }
}