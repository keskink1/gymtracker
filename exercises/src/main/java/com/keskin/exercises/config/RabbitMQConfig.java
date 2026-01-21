package com.keskin.exercises.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "exercise.exchange";

    public static final String CREATE_QUEUE = "exercise.create.queue";
    public static final String DELETE_QUEUE = "exercise.delete.queue";

    public static final String CREATE_ROUTING_KEY = "exercise.created";
    public static final String DELETE_ROUTING_KEY = "exercise.deleted";

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Queue createQueue() {
        return new Queue(CREATE_QUEUE);
    }

    @Bean
    public Queue deleteQueue() {
        return new Queue(DELETE_QUEUE);
    }

    @Bean
    public Binding createBinding(Queue createQueue, TopicExchange exchange) {
        return BindingBuilder.bind(createQueue).to(exchange).with(CREATE_ROUTING_KEY);
    }

    @Bean
    public Binding deleteBinding(Queue deleteQueue, TopicExchange exchange) {
        return BindingBuilder.bind(deleteQueue).to(exchange).with(DELETE_ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}