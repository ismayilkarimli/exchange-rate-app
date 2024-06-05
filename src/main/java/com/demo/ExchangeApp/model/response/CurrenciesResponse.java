package com.demo.ExchangeApp.model.response;

import java.util.List;

public record CurrenciesResponse(
        List<CurrencyResponse> currencies
) { }
