package ru.mipt.smartslame.pdris.service;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.mipt.smartslame.pdris.entity.WeatherStamp;
import ru.mipt.smartslame.pdris.model.WeatherStampList;
import ru.mipt.smartslame.pdris.repository.WeatherRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class WeatherService {
    private final WeatherRepository weatherRepository;
    private final RestTemplate restTemplate;
    private final String apiKey;
    private final String apiUrl;
    private final String defaultCity;
    private final Map<String, Integer> cache;

    public WeatherService(WeatherRepository weatherRepository, RestTemplate restTemplate, String apiKey, String apiUrl, String defaultCity) {
        this.weatherRepository = weatherRepository;
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
        this.apiUrl = apiUrl;
        this.defaultCity = defaultCity;
        this.cache = new HashMap<>();
    }

    private WeatherStampList getWeatherFromApi(String city, String dateFrom, String dateTo) {
        String requestUri = UriComponentsBuilder
                .fromHttpUrl(apiUrl)
                .queryParam("key", apiKey)
                .queryParam("q", city)
                .queryParam(
                        "dt",
                        dateFrom
                )
                .queryParam(
                        "end_dt",
                        dateTo
                )
                .toUriString();

        return restTemplate.getForObject(requestUri, WeatherStampList.class);
    }

    public WeatherStampList getWeather(int daysCount, String city) {
        if (Objects.isNull(city)) {
            city = defaultCity;
        }
        LocalDate dateTo = LocalDate.now();
        LocalDate dateFrom = dateTo.minusDays(daysCount - 1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        WeatherStampList weatherFromDb = new WeatherStampList(
                city,
                weatherRepository.findAllByCityAndDateBetween(city, dateFrom, dateTo)
        );

        if (cache.getOrDefault(city, 0) >= daysCount) {
            return weatherFromDb;
        }

        WeatherStampList weatherFromApi = getWeatherFromApi(city, dateFrom.format(formatter), dateTo.format(formatter));

        List<WeatherStamp> stampsToSave = weatherFromApi.getWeatherStamps()
                .stream()
                .filter(apiStamp -> weatherFromDb.getWeatherStamps().stream().noneMatch(dbStamp -> dbStamp.getDate().isEqual(apiStamp.getDate())))
                .collect(Collectors.toList());

        weatherRepository.saveAll(stampsToSave);
        cache.compute(city, (k, v) -> v = daysCount);

        return weatherFromApi;
    }

    public WeatherStampList getWeather(int daysCount) {
        return getWeather(daysCount, null);
    }


}
