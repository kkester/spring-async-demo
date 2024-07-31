package com.example.asyncdemo.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

@Slf4j
public class AsyncApplicationEventListener implements ApplicationListener<ApplicationEvent> {

    private ApplicationEvent previousApplicationEvent;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (previousApplicationEvent == null) {
            log.info("Handling application event {}", event.getClass().getSimpleName());
        } else {
            log.info("Handling application event {} after {}", event.getClass().getSimpleName(), previousApplicationEvent.getClass().getSimpleName());
        }
        previousApplicationEvent = event;
    }
}
