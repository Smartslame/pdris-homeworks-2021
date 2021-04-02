package ru.mipt.smartslame.pdris.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.mipt.smartslame.pdris.model.WeatherStampList;
import ru.mipt.smartslame.pdris.service.WeatherService;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Controller
@Validated
public class WeatherController {
    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    WeatherStampList getWeather(
            @RequestParam(defaultValue = "1")
            @Min(value = 1, message = "n should be >= 1")
            @Max(value = 7, message = "n should be <= 7") int n,
            @RequestParam(required = false) String city) {
        return weatherService.getWeather(n, city);
    }
}
