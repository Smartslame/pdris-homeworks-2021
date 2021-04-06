package ru.mipt.smartslame.pdris.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "weather")
public class WeatherStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private String city;
    private double minTemp;
    private double maxTemp;
    private double avgTemp;
    private double maxWindSpeed;
    private double avgVisibility;

    public WeatherStamp() {
    }

    public WeatherStamp(String city, LocalDate date, double minTemp, double maxTemp, double avgTemp, double maxWindSpeed, double avgVisibility) {
        this.city = city;
        this.date = date;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.avgTemp = avgTemp;
        this.maxWindSpeed = maxWindSpeed;
        this.avgVisibility = avgVisibility;
    }

    @JsonIgnore
    public String getCity() {
        return city;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public double getAvgTemp() {
        return avgTemp;
    }

    public double getMaxWindSpeed() {
        return maxWindSpeed;
    }

    public double getAvgVisibility() {
        return avgVisibility;
    }

    public LocalDate getDate() {
        return date;
    }
}
