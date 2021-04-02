package ru.mipt.smartslame.pdris;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.mipt.smartslame.pdris.service.PredictionService;

@Configuration
public class AppConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public PredictionService predictionService(
            DiscoveryClient discoveryClient,
            RestTemplate restTemplate,
            @Value("${weather.api.days.limit}") int daysLimit,
            @Value("${weather.service.name}") String weatherServiceName,
            @Value("${currency.service.name}") String currencyServiceName) {
        return new PredictionService(discoveryClient, restTemplate, daysLimit, weatherServiceName, currencyServiceName);
    }
}
