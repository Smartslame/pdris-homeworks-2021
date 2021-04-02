package ru.mipt.smartslame.pdris.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;


public class WeatherStamp {
    private LocalDate date;
    private double avgTemp;

    public WeatherStamp() {
    }

    public WeatherStamp(LocalDate date, double avgTemp) {
        this.date = date;
        this.avgTemp = avgTemp;
    }

    public double getAvgTemp() {
        return avgTemp;
    }
    public LocalDate getDate() {
        return date;
    }
}
