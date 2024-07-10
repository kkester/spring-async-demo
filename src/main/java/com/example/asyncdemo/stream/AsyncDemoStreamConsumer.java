package com.example.asyncdemo.stream;

import com.example.asyncdemo.job.AsyncDemoService;
import com.example.asyncdemo.job.JobEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.example.asyncdemo.stream.EventBusConfig.Q_ASYNC_JOB;

@Component
@RequiredArgsConstructor
@Slf4j
public class AsyncDemoStreamConsumer {

    private final AsyncDemoService service;

    @RabbitListener(queues = Q_ASYNC_JOB)
    public void handle(JobEntity job) {
        log.info("Stream starting for job {}", job);

        service.processJob(job.getId());

        log.info("Stream Completing for job {}. VT: {}", job, Thread.currentThread().isVirtual());
    }
}
