package com.demo.ExchangeApp.job;

import com.demo.ExchangeApp.service.ExchangeRateService;
import com.demo.ExchangeApp.service.FxApiManagerService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Component
public class DailyCurrencyRateUpdateJob implements Job {

    private final FxApiManagerService fxApiService;
    private final ExchangeRateService exchangeRateService;

    public DailyCurrencyRateUpdateJob(ExchangeRateService exchangeRateService, FxApiManagerService fxApiService) {
        this.exchangeRateService = exchangeRateService;
        this.fxApiService = fxApiService;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        var currencies = fxApiService.getCurrencyList();
        for (var currency : currencies) {
            exchangeRateService.saveExchangeRate(currency.ccy());
        }
    }

}
