package com.example.asyncdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class AsyncDemoScheduler {

    @Autowired
    private AsyncDemoService asyncDemoService;

    @Scheduled(fixedDelay = 2000)
    public void perform() {

        System.out.println("Scheduler Started at :" + new Date());

        try {
            asyncDemoService.retryFailedJobs();
        }
        catch (TaskRejectedException ex) {
            System.out.println("Scheduler task rejected at :" + new Date());
        }

        System.out.println("Scheduler finished at :" + new Date());
    }

}
