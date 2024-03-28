package ru.nsu.robertoriy.manager.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.core.publisher.Mono;
import ru.nsu.robertoriy.manager.client.WorkerClient;

@Configuration
public class ClientConfig {
    @Bean
    public WorkerClient managerClient(ApplicationConfig applicationConfig, WebClient.Builder webClientBuilder) {
        WebClient webClient = webClientBuilder
            .defaultStatusHandler(code -> true, clientResponse -> Mono.empty())
            .defaultHeader("Content-Type", "application/json")
            .baseUrl(applicationConfig.workerUrl())
            .build();

        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory
            .builderFor(WebClientAdapter.create(webClient))
            .build();

        return httpServiceProxyFactory.createClient(WorkerClient.class);
    }
}
