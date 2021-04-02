package ru.mipt.smartslame.pdris.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.mipt.smartslame.pdris.service.PredictionService;

@Controller
public class PredictionController {
    private final PredictionService predictionService;

    public PredictionController(PredictionService predictionService) {
        this.predictionService = predictionService;
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    double getCurrencyPrediction(@RequestParam(defaultValue = "0") double avgTemp) {
        return predictionService.getCurrencyPrediction(avgTemp);
    }
}
