package com.example.asyncdemo.event;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

@Configuration
public class AsyncDemoConfig {

    @Bean
    public Executor asyncExecutor() {
        ThreadFactory factory = Thread.ofVirtual().name("virtual-task").factory();
        return Executors.newThreadPerTaskExecutor(factory);
    }

    @Bean
    public ApplicationEventMulticaster applicationEventMulticaster(Executor asyncExecutor) {
        SimpleApplicationEventMulticaster eventMulticaster = new SimpleApplicationEventMulticaster();
        eventMulticaster.setTaskExecutor(asyncExecutor);
        return eventMulticaster;
    }

}
