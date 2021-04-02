package ru.mipt.smartslame.pdris.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.mipt.smartslame.pdris.entity.WeatherStamp;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class WeatherServiceTest {
    @Autowired
    private WeatherService weatherService;

    @Test
    public void getWeatherWithoutCityTest() {
        List<WeatherStamp> weatherStamps = weatherService.getWeather(1).getWeatherStamps();
        assertNotNull(weatherStamps);
        assertEquals(1, weatherStamps.size());
        WeatherStamp weatherStamp = weatherStamps.get(0);
        assertEquals("Moscow", weatherStamp.getCity());
    }

    @Test
    public void getWeatherWithCityTest() {
        System.out.println();
        List<WeatherStamp> weatherStamps = weatherService.getWeather(1, "Khabarovsk").getWeatherStamps();
        assertNotNull(weatherStamps);
        assertEquals(1, weatherStamps.size());
        WeatherStamp weatherStamp = weatherStamps.get(0);
        assertEquals("Khabarovsk", weatherStamp.getCity());
    }
}