package ru.nsu.robertoriy.manager;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.nsu.robertoriy.manager.configuration.ApplicationConfig;

@Slf4j
@Component
public class Manager {
    int maxWordLength;
    int partCount;

    public Manager(ApplicationConfig applicationConfig) {
        maxWordLength = applicationConfig.maxWordLength();
        partCount = applicationConfig.partCount();
    }

    @PostConstruct
    public void test() {
        log.info(String.valueOf(maxWordLength));
        log.info(String.valueOf(partCount));
    }
}
