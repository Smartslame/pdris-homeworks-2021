package ru.mipt.smartslame.pdris.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.mipt.smartslame.pdris.entity.WeatherStamp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherStampList {
    private String city;
    private List<WeatherStamp> weatherStamps;

    public WeatherStampList() {
    }

    public WeatherStampList(String city, List<WeatherStamp> weatherStamps) {
        this.city = city;
        this.weatherStamps = weatherStamps;
    }

    @JsonProperty("city")
    public String getCity() {
        return city;
    }

    public List<WeatherStamp> getWeatherStamps() {
        return weatherStamps;
    }

    @JsonProperty("location")
    private void setCity(Map<String, Object> location) {
        this.city = (String) location.get("name");
    }

    @JsonProperty("forecast")
    private void setWeatherStamps(Map<String, Object> forecast) {
        ArrayList<Map<String, Object>> daylyForecasts = (ArrayList<Map<String, Object>>) forecast.get("forecastday");
        this.weatherStamps = daylyForecasts.stream()
                .map(daylyForecast -> {
                    LocalDate date = LocalDate.parse((String) daylyForecast.get("date"));
                    Map<String, Object> stats = (Map<String, Object>) daylyForecast.get("day");
                    return new WeatherStamp(
                            this.city,
                            date,
                            (double) stats.get("mintemp_c"),
                            (double) stats.get("maxtemp_c"),
                            (double) stats.get("avgtemp_c"),
                            (double) stats.get("maxwind_kph"),
                            (double) stats.get("avgvis_km")
                    );
                })
                .collect(Collectors.toList());
    }
}
