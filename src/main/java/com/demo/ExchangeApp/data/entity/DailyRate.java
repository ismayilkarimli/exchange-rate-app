package com.demo.ExchangeApp.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class DailyRate {
    @Id
    @GeneratedValue
    private Long id;
    private String baseCurrency;
    private String targetCurrency;
    private Double conversionRate;
    private LocalDate date;

    public DailyRate(String baseCurrency, String targetCurrency, Double conversionRate, LocalDate date) {
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.conversionRate = conversionRate;
        this.date = date;
    }

    public DailyRate() { }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public Double getConversionRate() {
        return conversionRate;
    }

    public void setConversionRate(Double conversionRate) {
        this.conversionRate = conversionRate;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DailyRate dailyRate = (DailyRate) o;

        if (!id.equals(dailyRate.id)) return false;
        if (!baseCurrency.equals(dailyRate.baseCurrency)) return false;
        if (!targetCurrency.equals(dailyRate.targetCurrency)) return false;
        if (!conversionRate.equals(dailyRate.conversionRate)) return false;
        return date.equals(dailyRate.date);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + baseCurrency.hashCode();
        result = 31 * result + targetCurrency.hashCode();
        result = 31 * result + conversionRate.hashCode();
        result = 31 * result + date.hashCode();
        return result;
    }
}
