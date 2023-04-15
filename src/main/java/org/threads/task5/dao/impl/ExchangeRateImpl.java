package org.threads.task5.dao.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.threads.task5.dao.ExchangeRateDao;
import org.threads.task5.models.Currency;
import org.threads.task5.models.ExchangeRate;

public class ExchangeRateImpl implements ExchangeRateDao {

    private DataManager parser;

    public ExchangeRateImpl(final DataManager parser) {
        this.parser = parser;
    }

    @Override
    public BigDecimal getExchangeRate(final Currency from, final Currency to, final LocalDate date) {
        return getAllRates().stream().filter(rate -> rate.getFrom().equals(from)
                        && rate.getTo().equals(to)
                        && rate.getDate().equals(date))
                .map(ExchangeRate::getRate).findAny().orElseThrow(()->new RuntimeException("no rate found"));
    }

    private List<ExchangeRate> getAllRates() {
      List<String> list= List.of("rate1.json","rate2.json");
       return list.stream().map(filename-> {
            try {
                return parser.parseExchangeFile(filename);
            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }
       }).toList();
    }




}
