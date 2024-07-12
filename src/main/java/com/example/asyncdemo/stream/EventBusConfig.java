package com.example.asyncdemo.stream;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class EventBusConfig {
    public static final String ASYNC_EXCHANGE = "async";
    public static final String JOB_ROUTING_KEY = "job-queue";
    public static final String Q_ASYNC_JOB = "async-job-queue";

    public final CachingConnectionFactory connectionFactory;

    @Bean
    public RabbitTemplate jsonRabbitTemplate(Jackson2JsonMessageConverter jsonMessageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter);
        rabbitTemplate.setExchange(ASYNC_EXCHANGE);
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    Declarables amqpAsyncDeclarables() {
        // Commerce queues and bindings
        var asyncExchange = new TopicExchange(ASYNC_EXCHANGE);

        var asyncJobQueue = new Queue(Q_ASYNC_JOB);
        var asyncJobBinding = BindingBuilder.bind(asyncJobQueue)
            .to(asyncExchange)
            .with(JOB_ROUTING_KEY);

        return new Declarables(
            asyncExchange,
            asyncJobQueue,
            asyncJobBinding);
    }
}
