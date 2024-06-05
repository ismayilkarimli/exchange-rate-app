package com.demo.ExchangeApp.model.response;

public record HistoricalRateResponse(
        String targetCurrency,
        double rate,
        String date
) { }
