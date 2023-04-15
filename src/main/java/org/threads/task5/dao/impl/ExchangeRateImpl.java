package org.threads.task5.dao.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.parser.ParseException;
import org.threads.task5.CustomExceptions.ExchangeRateNotFoundException;
import org.threads.task5.CustomExceptions.FileParsingException;
import org.threads.task5.dao.ExchangeRateDao;
import org.threads.task5.models.Currency;
import org.threads.task5.models.ExchangeRate;

public class ExchangeRateImpl implements ExchangeRateDao {

    private DataManager parser;

    private static final Logger logger= Logger.getLogger(ExchangeRateImpl.class.getName());

    public ExchangeRateImpl(final DataManager parser) {
        this.parser = parser;
    }

    @Override
    public BigDecimal getExchangeRate(final Currency from, final Currency to, final LocalDate date) {
        return getAllRates().stream().filter(rate -> rate.getFrom().equals(from)
                        && rate.getTo().equals(to)
                        && rate.getDate().equals(date))
                .map(ExchangeRate::getRate)
                .peek((rate)->logger.log(Level.INFO,"exchange rate is "+rate))
                .findAny()
                .orElseThrow(()->new ExchangeRateNotFoundException("no rate found between currencies from "+from+" to "+ to+" for date "+date));
    }

    private List<ExchangeRate> getAllRates() {
      List<String> list= List.of("rate1.json","rate2.json");
       return list.stream().map(filename-> {
            try {
                return parser.parseExchangeFile(filename);
            } catch (IOException | ParseException e) {
                throw new FileParsingException("can not parse this file "+filename);
            }
       }).toList();
    }




}
