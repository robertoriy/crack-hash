package ru.nsu.robertoriy.worker.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.core.publisher.Mono;
import ru.nsu.robertoriy.worker.client.ManagerClient;

@Configuration
public class ClientConfig {
    @Bean
    public ManagerClient managerClient(ApplicationConfig applicationConfig, WebClient.Builder webClientBuilder) {
        WebClient webClient = webClientBuilder
            .defaultStatusHandler(httpStatusCode -> true, clientResponse -> Mono.empty())
            .defaultHeader("Content-Type", "application/json")
            .baseUrl(applicationConfig.managerUrl())
            .build();

        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory
            .builderFor(WebClientAdapter.create(webClient))
            .build();

        return httpServiceProxyFactory.createClient(ManagerClient.class);
    }
}
