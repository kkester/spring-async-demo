package com.example.asyncdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class AsyncDemoScheduler {

    @Autowired
    private AsyncDemoService asyncDemoService;

    @Scheduled(fixedDelay = 2000)
    public void perform() {

        log.info("Scheduler Started at :" + new Date());

        try {
            asyncDemoService.retryFailedJobs();
        }
        catch (TaskRejectedException ex) {
            log.info("Scheduler task rejected at :" + new Date());
        }

        log.info("Scheduler finished at :" + new Date());
    }

}
