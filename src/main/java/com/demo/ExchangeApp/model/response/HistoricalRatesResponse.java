package com.demo.ExchangeApp.model.response;

import java.util.List;

public record HistoricalRatesResponse(
        List<HistoricalRateResponse> rates
) { }
