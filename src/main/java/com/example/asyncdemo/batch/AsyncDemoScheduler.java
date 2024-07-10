package com.example.asyncdemo.batch;

import com.example.asyncdemo.job.AsyncDemoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AsyncDemoScheduler {

    private final AsyncDemoService asyncDemoService;

    @Scheduled(fixedDelay = 20000)
    public void perform() {

        log.info("Scheduler Started");

        try {
            asyncDemoService.retryFailedJobs();
        } catch (TaskRejectedException ex) {
            log.info("Scheduler task rejected due to", ex);
        }

        log.info("Scheduler finished. VT: {}", Thread.currentThread().isVirtual());
    }

}
