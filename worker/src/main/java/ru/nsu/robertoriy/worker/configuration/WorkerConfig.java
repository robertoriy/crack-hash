package ru.nsu.robertoriy.worker.configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WorkerConfig {
    @Bean(destroyMethod = "shutdown")
    public ExecutorService getExecutorService() {
        return Executors.newCachedThreadPool();
    }
}
