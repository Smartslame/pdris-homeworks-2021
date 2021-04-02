package ru.mipt.smartslame.pdris;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.mipt.smartslame.pdris.repository.WeatherRepository;
import ru.mipt.smartslame.pdris.service.WeatherService;

@Configuration
public class AppConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public WeatherService weatherService(
            WeatherRepository weatherRepository,
            RestTemplate restTemplate,
            @Value("${weather.api.key}") String apiKey,
            @Value("${weather.api.url}") String apiUrl,
            @Value("${weather.default.city}") String defaultCity) {
        return new WeatherService(weatherRepository, restTemplate, apiKey, apiUrl, defaultCity);
    }
}
