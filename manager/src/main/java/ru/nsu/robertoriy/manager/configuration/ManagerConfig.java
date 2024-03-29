package ru.nsu.robertoriy.manager.configuration;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.nsu.robertoriy.manager.dto.response.StatusResponse;

@Configuration
public class ManagerConfig {
    @Bean
    @Scope("prototype")
    public Map<UUID, StatusResponse> getStatusResponseMap() {
        return new ConcurrentHashMap<>();
    }

    @Bean(destroyMethod = "shutdown")
    public ScheduledExecutorService getScheduler() {
        return Executors.newSingleThreadScheduledExecutor();
    }

    @Bean
    @Scope("prototype")
    public Map<UUID, Integer> getCounterMap() {
        return new ConcurrentHashMap<>();
    }
}
