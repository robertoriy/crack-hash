package ru.nsu.robertoriy.worker;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.nsu.robertoriy.worker.configuration.ApplicationConfig;

@Slf4j
@Component
public class Worker {
    long value;

    public Worker(ApplicationConfig applicationConfig) {
        value = applicationConfig.workerValue();
    }

    @PostConstruct
    public void test() {
        log.info(String.valueOf(value));
    }
}
