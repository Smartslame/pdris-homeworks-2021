package ru.mipt.smartslame.pdris.service;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.mipt.smartslame.pdris.entity.CurrencyStamp;
import ru.mipt.smartslame.pdris.model.CurrencyStampList;
import ru.mipt.smartslame.pdris.repository.CurrencyRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class CurrencyService {
    private final CurrencyRepository currencyRepository;
    private final RestTemplate restTemplate;
    private final String apiUrl;
    private final String defaultCurrencyId;
    private final Map<String, Integer> cache;

    public CurrencyService(CurrencyRepository currencyRepository, RestTemplate restTemplate, String apiUrl, String defaultCurrencyId) {
        this.currencyRepository = currencyRepository;
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
        this.defaultCurrencyId = defaultCurrencyId;
        this.cache = new HashMap<>();
    }

    private CurrencyStampList getCurrenciesFromApi(String currencyId, String dateFrom, String dateTo) {
        String requestUri = UriComponentsBuilder
                .fromHttpUrl(apiUrl)
                .queryParam("VAL_NM_RQ", currencyId)
                .queryParam(
                        "date_req1",
                        dateFrom
                )
                .queryParam(
                        "date_req2",
                        dateTo
                )
                .toUriString();

        return restTemplate.getForObject(requestUri, CurrencyStampList.class);
    }

    public CurrencyStampList getCurrencies(int daysCount, String currencyId) {
        if (Objects.isNull(currencyId)) {
            currencyId = defaultCurrencyId;
        }
        LocalDate dateTo = LocalDate.now();
        LocalDate dateFrom = dateTo.minusDays(daysCount - 1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        CurrencyStampList currenciesFromDb = new CurrencyStampList(
                currencyId,
                currencyRepository.findAllByCurrencyIdAndDateBetween(currencyId, dateFrom, dateTo)
        );

        if (cache.getOrDefault(currencyId, 0) >= daysCount) {
            return currenciesFromDb;
        }

        CurrencyStampList currenciesFromApi = getCurrenciesFromApi(currencyId, dateFrom.format(formatter), dateTo.format(formatter));

        List<CurrencyStamp> stampsToSave = currenciesFromApi.getCurrencyStamps()
                .stream()
                .filter(apiStamp -> currenciesFromDb.getCurrencyStamps().stream().noneMatch(dbStamp -> dbStamp.getDate().isEqual(apiStamp.getDate())))
                .collect(Collectors.toList());

        currencyRepository.saveAll(stampsToSave);
        cache.compute(currencyId, (k, v) -> v = daysCount);
        return currenciesFromApi;

    }

    public CurrencyStampList getCurrencies(int daysCount) {
        return getCurrencies(daysCount, null);
    }

}
