package com.demo.ExchangeApp.service;

import com.demo.ExchangeApp.data.repository.DailyRateRepository;
import com.demo.ExchangeApp.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.util.List;

@Service
public class FxApiManagerService {

    private final RestClient restClient;

    public FxApiManagerService(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("https://www.lb.lt/webservices/FxRates/FxRates.asmx")
                .build();
    }

    public FxRates getFxRatesForCurrency(String currency, LocalDate dateFrom, LocalDate dateTo) {
        return restClient.get()
                .uri(String.format("/getFxRatesForCurrency?tp=&ccy=%s&dtFrom=%s&dtTo=%s", currency, dateFrom, dateTo))
                .retrieve()
                .toEntity(FxRates.class)
                .getBody();
    }

    public FxDateRate getFxRateForCurrencyToday(String currency) {
        var today = LocalDate.now();

        var response = restClient.get()
                .uri(String.format("/getFxRatesForCurrency?tp=&ccy=%s&dtFrom=%s&dtTo=", currency, today))
                .retrieve()
                .toEntity(FxRates.class)
                .getBody();
        if (response.fxRates() == null || response.fxRates().isEmpty()) {
            return null;
        }
        var date = response.fxRates().getFirst().dt();
        var ccyAmt = response.fxRates().getFirst().ccyAmts().getLast();
        return new FxDateRate(LocalDate.parse(date), ccyAmt.ccy(), ccyAmt.amt());
    }

    public List<CcyNtry> getCurrencyList() {
        var response = restClient.get()
                .uri("/getCurrencyList")
                .retrieve()
                .toEntity(CcyTbl.class)
                .getBody();
        return response.currencies();
    }
}
