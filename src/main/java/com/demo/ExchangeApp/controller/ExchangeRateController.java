package com.demo.ExchangeApp.controller;

import com.demo.ExchangeApp.model.FxRates;
import com.demo.ExchangeApp.model.response.CurrenciesResponse;
import com.demo.ExchangeApp.model.response.HistoricalRatesResponse;
import com.demo.ExchangeApp.service.ExchangeRateService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;

    public ExchangeRateController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @GetMapping(value = "/history", produces = "application/json")
    public ResponseEntity<HistoricalRatesResponse> getRateHistory(@RequestParam String currency) {
        var rateHistory = exchangeRateService.getRateHistory(currency);
        return new ResponseEntity<>(rateHistory, HttpStatus.OK);
    }

    @GetMapping(value = "/exchangeRate", produces = "application/json")
    public ResponseEntity<Map> getExchangeRateForCurrency(@RequestParam String targetCurrency) {
        var conversionRate = exchangeRateService.getExchangeRateForCurrency(targetCurrency);
        return new ResponseEntity<>(Map.of("rate", conversionRate), HttpStatus.OK);
    }

    @GetMapping(value = "/currencies", produces = "application/json")
    public ResponseEntity<CurrenciesResponse> getCurrencies() {
        var currencies = exchangeRateService.getCurrencies();
        return new ResponseEntity(currencies, HttpStatus.OK);
    }
}
