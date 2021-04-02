package ru.mipt.smartslame.pdris.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.mipt.smartslame.pdris.entity.WeatherStamp;

import java.util.List;

public class WeatherStampList {
    private String city;
    private List<WeatherStamp> weatherStamps;

    public WeatherStampList() {
    }

    public WeatherStampList(String city, List<WeatherStamp> weatherStamps) {
        this.city = city;
        this.weatherStamps = weatherStamps;
    }

    @JsonProperty("forecast")
    public List<WeatherStamp> getWeatherStamps() {
        return weatherStamps;
    }

    public String getCity() {
        return city;
    }
}
