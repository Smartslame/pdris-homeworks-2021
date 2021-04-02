package ru.mipt.smartslame.pdris.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.mipt.smartslame.pdris.entity.CurrencyStamp;

import java.util.List;


public class CurrencyStampList {
    private String id;
    private List<CurrencyStamp> currencyStamps;

    public CurrencyStampList() {
    }

    public CurrencyStampList(String id, List<CurrencyStamp> currencyStamps) {
        this.id = id;
        this.currencyStamps = currencyStamps;
    }

    @JsonProperty("dynamic")
    public List<CurrencyStamp> getCurrencyStamps() {
        return currencyStamps;
    }

    public String getId() {
        return id;
    }
}