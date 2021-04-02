package ru.mipt.smartslame.pdris.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import ru.mipt.smartslame.pdris.entity.CurrencyStamp;

import java.util.List;


@JacksonXmlRootElement(localName = "ValCurs")
public class CurrencyStampList {
    @JacksonXmlProperty(localName = "ID", isAttribute = true)
    private String id;
    @JacksonXmlProperty(localName = "Record")
    @JacksonXmlElementWrapper(useWrapping = false)
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