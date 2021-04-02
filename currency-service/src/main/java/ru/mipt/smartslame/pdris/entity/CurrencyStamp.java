package ru.mipt.smartslame.pdris.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import ru.mipt.smartslame.pdris.jackson.custom.DateDeserializer;
import ru.mipt.smartslame.pdris.jackson.custom.DoubleDeserializer;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "currency")
@JacksonXmlRootElement(localName = "Record")
public class CurrencyStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JacksonXmlProperty(localName = "Id", isAttribute = true)
    private String currencyId;

    @JacksonXmlProperty(localName = "Date", isAttribute = true)
    @JsonDeserialize(using = DateDeserializer.class)
    private LocalDate date;

    @JacksonXmlProperty(localName = "Value")
    @JsonDeserialize(using = DoubleDeserializer.class)
    private double cost;

    public CurrencyStamp() {
    }

    public CurrencyStamp(String currencyId, LocalDate date, double cost) {
        this.currencyId = currencyId;
        this.date = date;
        this.cost = cost;
    }

    @JsonIgnore
    public String getCurrencyId() {
        return currencyId;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getCost() {
        return cost;
    }
}
