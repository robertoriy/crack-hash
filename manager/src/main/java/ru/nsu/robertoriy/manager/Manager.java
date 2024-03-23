package ru.nsu.robertoriy.manager;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.nsu.robertoriy.manager.configuration.ApplicationConfig;

@Slf4j
@Component
public class Manager {
    long value;

    public Manager(ApplicationConfig applicationConfig) {
        value = applicationConfig.maxWordLength();
    }

    @PostConstruct
    public void test() {
        log.info(String.valueOf(value));
    }
}
