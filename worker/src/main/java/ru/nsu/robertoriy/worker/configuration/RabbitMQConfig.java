package ru.nsu.robertoriy.worker.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean
    public DirectExchange directExchange(RabbitMQPropertiesConfig config) {
        return new DirectExchange(config.exchangeName(), true, false);
    }

    @Bean
    public Queue taskQueue(RabbitMQPropertiesConfig config) {
        return QueueBuilder.durable(config.taskQueue()).build();
    }

    @Bean
    public Queue resultQueue(RabbitMQPropertiesConfig config) {
        return QueueBuilder.durable(config.resultQueue()).build();
    }

    @Bean
    public Binding bindingTasks(Queue taskQueue, DirectExchange directExchange, RabbitMQPropertiesConfig config) {
        return BindingBuilder.bind(taskQueue)
            .to(directExchange)
            .with(config.taskRoutingKey());
    }

    @Bean
    public Binding bindingResults(Queue resultQueue, DirectExchange directExchange, RabbitMQPropertiesConfig config) {
        return BindingBuilder.bind(resultQueue)
            .to(directExchange)
            .with(config.resultRoutingKey());
    }
}
