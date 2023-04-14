package org.threads.task5.dao.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.threads.task5.dao.ExchangeRateDao;
import org.threads.task5.models.Currency;
import org.threads.task5.models.ExchangeRate;

public class ExchangeRateImpl implements ExchangeRateDao {

    @Override
    public BigDecimal getExchangeRate(final Currency from, final Currency to, final LocalDate date) {


        return null;
    }

    private List<ExchangeRate> getAllRates(){
        return null;
    }
}
