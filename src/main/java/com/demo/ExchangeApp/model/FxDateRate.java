package com.demo.ExchangeApp.model;

import java.time.LocalDate;

public record FxDateRate(
        LocalDate date,
        String currency,
        Double rate
) {
}
