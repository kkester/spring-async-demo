package com.example.asyncdemo.stream;

import com.example.asyncdemo.job.JobEntity;
import com.example.asyncdemo.job.JobStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.MessageHandlingException;
import org.springframework.stereotype.Component;

import static com.example.asyncdemo.stream.EventBusConfig.JOB_ROUTING_KEY;

@Component
@RequiredArgsConstructor
@Slf4j
public class AsyncDemoStreamProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(JobEntity job) {

        log.info("Sending Job {}", job.getId());

        try {
            rabbitTemplate.convertAndSend(JOB_ROUTING_KEY, job);
        } catch (MessageHandlingException e) {
            job.setStatus(JobStatus.FAILED);
        }
    }

}
