package ru.mipt.smartslame.pdris;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.mipt.smartslame.pdris.repository.CurrencyRepository;
import ru.mipt.smartslame.pdris.service.CurrencyService;

@Configuration
public class AppConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public CurrencyService currencyService(
            CurrencyRepository currencyRepository,
            RestTemplate restTemplate,
            @Value("${currency.api.url}") String apiUrl,
            @Value("${currency.default.id}") String defaultCurrencyId) {
        return new CurrencyService(currencyRepository, restTemplate, apiUrl, defaultCurrencyId);
    }
}
