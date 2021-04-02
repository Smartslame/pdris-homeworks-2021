package ru.mipt.smartslame.pdris.entity;

import java.time.LocalDate;

public class CurrencyStamp {
    private LocalDate date;
    private double cost;

    public CurrencyStamp() {
    }

    public CurrencyStamp(LocalDate date, double cost) {
        this.date = date;
        this.cost = cost;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getCost() {
        return cost;
    }
}
