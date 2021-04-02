package ru.mipt.smartslame.pdris.service;

import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.mipt.smartslame.pdris.entity.CurrencyStamp;
import ru.mipt.smartslame.pdris.entity.WeatherStamp;
import ru.mipt.smartslame.pdris.model.CurrencyStampList;
import ru.mipt.smartslame.pdris.model.WeatherStampList;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PredictionService {
    private DiscoveryClient discoveryClient;
    private final RestTemplate restTemplate;
    private SimpleRegression regression;
    private final int daysLimit;
    private final String weatherServiceName;
    private final String currencyServiceName;

    public PredictionService(DiscoveryClient discoveryClient, RestTemplate restTemplate, int daysLimit, String weatherServiceName, String currencyServiceName) {
        this.discoveryClient = discoveryClient;
        this.restTemplate = restTemplate;
        this.daysLimit = daysLimit;
        this.weatherServiceName = weatherServiceName;
        this.currencyServiceName = currencyServiceName;
    }

    private void initRegression(int n) {
        List<CurrencyStamp> currencyStamps = getCurrenciesFromApi().getCurrencyStamps();
        List<WeatherStamp> weatherStamps = getWeatherFromApi().getWeatherStamps();


        List<WeatherStamp> weatherIntersection = weatherStamps.stream()
                .filter(weather -> currencyStamps.stream().anyMatch(currency -> currency.getDate().isEqual(weather.getDate())))
                .collect(Collectors.toList());

        regression = new SimpleRegression();

        for (int i = 0; i < weatherIntersection.size(); i++) {
            this.regression.addData(weatherIntersection.get(i).getAvgTemp(), currencyStamps.get(i).getCost());
        }
    }

    public double getCurrencyPrediction(double avgTemp) {
        if (Objects.isNull(regression)) {
            initRegression(this.daysLimit);
        }
        return this.regression.predict(avgTemp);
    }

    private CurrencyStampList getCurrenciesFromApi() {
        URI uri = discoveryClient.getInstances(this.currencyServiceName).stream().findAny().orElseThrow(RuntimeException::new).getUri();
        String requestUri = UriComponentsBuilder
                .fromUri(uri)
                .queryParam("n", this.daysLimit)
                .toUriString();

        return restTemplate.getForObject(requestUri, CurrencyStampList.class);
    }

    private WeatherStampList getWeatherFromApi() {
        URI uri = discoveryClient.getInstances(this.weatherServiceName).stream().findAny().orElseThrow(RuntimeException::new).getUri();
        String requestUri = UriComponentsBuilder
                .fromUri(uri)
                .queryParam("n", this.daysLimit)
                .toUriString();

        return restTemplate.getForObject(requestUri, WeatherStampList.class);
    }
}


