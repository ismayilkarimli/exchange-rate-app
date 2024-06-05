package com.demo.ExchangeApp.service;

import com.demo.ExchangeApp.data.entity.DailyRate;
import com.demo.ExchangeApp.data.repository.DailyRateRepository;
import com.demo.ExchangeApp.model.response.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExchangeRateService {

    private final DailyRateRepository repository;

    private final RestClient restClient;

    private final FxApiManagerService fxApiService;

    public ExchangeRateService(DailyRateRepository repository, FxApiManagerService fxApiService, RestClient.Builder builder) {
        this.repository = repository;
        this.fxApiService = fxApiService;
        this.restClient = builder
                .baseUrl("https://www.lb.lt/webservices/FxRates/FxRates.asmx")
                .build();
    }

    public HistoricalRatesResponse getRateHistory(String currency) {
        var today = LocalDate.now();
        var twoWeeksAgo = today.minusWeeks(2);

        var rates = fxApiService.getFxRatesForCurrency(currency, twoWeeksAgo, today);
        if (rates == null || rates.fxRates() == null || rates.fxRates().isEmpty()) {
            return new HistoricalRatesResponse(List.of());
        }

        var historicalRates = rates.fxRates().stream()
                .map(rate -> new HistoricalRateResponse(rate.ccyAmts().getLast().ccy(),
                                rate.ccyAmts().getLast().amt(),
                                rate.dt())).toList();
        return new HistoricalRatesResponse(historicalRates);
    }

    @Transactional
    public DailyRateResponse getExchangeRateForCurrency(String targetCurrency) {
        var today = LocalDate.now();
        var rateFromDb = repository.findRateByDate(targetCurrency, today);

        if (rateFromDb.isPresent()) {
            var dailyRateFromDb = rateFromDb.get();
            return new DailyRateResponse(dailyRateFromDb.getTargetCurrency(),
                    dailyRateFromDb.getConversionRate(),
                    dailyRateFromDb.getDate());
        }

        var dailyRateResponse = fxApiService.getFxRateForCurrencyToday(targetCurrency);
        if (dailyRateResponse == null) {
            return new DailyRateResponse(null, null, null);
        }
        repository.save(new DailyRate("EUR", targetCurrency, dailyRateResponse.rate(), today));

        return new DailyRateResponse(targetCurrency, dailyRateResponse.rate(), dailyRateResponse.date());
    }

    public void saveExchangeRate(String currency) {
        var optionalDailyRate = repository.findDailyRateByTargetCurrency(currency);
        var rateResponse = fxApiService.getFxRateForCurrencyToday(currency);
        if (rateResponse == null) {
            return;
        }

        if (optionalDailyRate.isPresent()) {
            var dailyRate = optionalDailyRate.get();
            dailyRate.setDate(LocalDate.now());
            dailyRate.setConversionRate(rateResponse.rate());
        } else {
            var dailyRate = new DailyRate("EUR", currency, rateResponse.rate(), LocalDate.now());
            repository.save(dailyRate);
        }
    }

    public CurrenciesResponse getCurrencies() {
        var xmlCurrencies = fxApiService.getCurrencyList();
        return new CurrenciesResponse(xmlCurrencies.stream()
                .map(currency -> new CurrencyResponse(currency.ccy()))
                .toList());
    }
}
