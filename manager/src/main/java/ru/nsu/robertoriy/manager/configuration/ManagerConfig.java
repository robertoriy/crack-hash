package ru.nsu.robertoriy.manager.configuration;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.nsu.robertoriy.manager.dto.response.StatusResponse;

@Configuration
public class BeanConfig {
    @Bean
    @Scope("prototype")
    public Map<UUID, StatusResponse> getStatusResponseMap() {
        return new ConcurrentHashMap<>();
    }
}
