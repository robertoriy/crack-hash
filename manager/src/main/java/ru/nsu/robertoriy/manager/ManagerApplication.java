package ru.nsu.robertoriy.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.nsu.robertoriy.manager.configuration.ApplicationConfig;
import ru.nsu.robertoriy.manager.configuration.RabbitMQPropertiesConfig;

@SpringBootApplication
@EnableConfigurationProperties({ApplicationConfig.class, RabbitMQPropertiesConfig.class})
public class ManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class, args);
    }
}
