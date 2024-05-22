package ru.nsu.robertoriy.worker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.nsu.robertoriy.worker.configuration.ApplicationConfig;
import ru.nsu.robertoriy.worker.configuration.RabbitMQPropertiesConfig;

@SpringBootApplication
@EnableConfigurationProperties({ApplicationConfig.class, RabbitMQPropertiesConfig.class})
public class WorkerApplication {
    public static void main(String[] args) {
        SpringApplication.run(WorkerApplication.class, args);
    }
}
