package com.demo.ExchangeApp.model.response;

import java.time.LocalDate;

public record DailyRateResponse(
        String currency,
        Double rate,
        LocalDate date
) {
}
