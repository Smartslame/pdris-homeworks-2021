package ru.mipt.smartslame.pdris.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.mipt.smartslame.pdris.model.CurrencyStampList;
import ru.mipt.smartslame.pdris.service.CurrencyService;

import javax.validation.constraints.Min;

@Controller
@Validated
public class CurrencyController {
    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    CurrencyStampList getCurrencies(
            @RequestParam(defaultValue = "1")
            @Min(value = 1, message = "n should be >= 1") int n,
            @RequestParam(required = false) String currencyId) {
        return currencyService.getCurrencies(n, currencyId);
    }
}
