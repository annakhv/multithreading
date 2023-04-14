package org.threads.task5.dao;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.threads.task5.models.Currency;

public interface ExchangeRateDao {

    BigDecimal getExchangeRate(Currency from, Currency to, LocalDate date);
}
