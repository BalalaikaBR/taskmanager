package com.taskmanager.rabbitmq;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;

@Configuration
public class TaskRabbitConfiguration {

    public static final String EXCHANGE = "task.exchange";

    // CREATE
    public static final String QUEUE_CREATE = "task.created.queue";
    public static final String ROUTING_KEY_CREATE = "task.created";

    // UPDATE
    public static final String QUEUE_UPDATE = "task.updated.queue";
    public static final String ROUTING_KEY_UPDATE = "task.updated";

    // DELETE
    public static final String QUEUE_DELETE = "task.deleted.queue";
    public static final String ROUTING_KEY_DELETE = "task.deleted";

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }

    // Queues
    @Bean
    public Queue queueCreate() {
        return QueueBuilder.durable(QUEUE_CREATE).build();
    }

    @Bean
    public Queue queueUpdate() {
        return QueueBuilder.durable(QUEUE_UPDATE).build();
    }

    @Bean
    public Queue queueDelete() {
        return QueueBuilder.durable(QUEUE_DELETE).build();
    }

    // Bindings
    @Bean
    public Binding bindingCreate() {
        return BindingBuilder.bind(queueCreate())
                .to(exchange())
                .with(ROUTING_KEY_CREATE);
    }

    @Bean
    public Binding bindingUpdate() {
        return BindingBuilder.bind(queueUpdate())
                .to(exchange())
                .with(ROUTING_KEY_UPDATE);
    }

    @Bean
    public Binding bindingDelete() {
        return BindingBuilder.bind(queueDelete())
                .to(exchange())
                .with(ROUTING_KEY_DELETE);
    }
}
