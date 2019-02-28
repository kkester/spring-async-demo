package com.example.asyncdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageHandlingException;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(Source.class)
@Slf4j
public class AsyncDemoStreamProducer {

    @Autowired
    private Source source;

    @SendTo(Source.OUTPUT)
    public void sendMessage(JobEntity job) {

        log.info("Sending Job {}", job.getId());

        try {
            source.output().send(MessageBuilder.withPayload(job).build());
        } catch (MessageHandlingException e) {
            job.setStatus("failed");
        }
    }

}
