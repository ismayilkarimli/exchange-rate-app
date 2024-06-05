package com.demo.ExchangeApp.data.repository;

import com.demo.ExchangeApp.data.entity.DailyRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface DailyRateRepository extends JpaRepository<DailyRate, Long> {

    @Query("SELECT er " +
            "FROM DailyRate er " +
            "WHERE er.baseCurrency = 'EUR' " +
            "AND er.targetCurrency = :targetCurrency " +
            "AND er.date = :date")
    Optional<DailyRate> findRateByDate(@Param("targetCurrency") String targetCurrency, @Param("date") LocalDate date);

    Optional<DailyRate> findDailyRateByTargetCurrency(String targetCurrency);

}
