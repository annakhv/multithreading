package org.threads.task5;

import org.json.simple.parser.ParseException;
import org.threads.task5.models.Amount;
import org.threads.task5.models.Currency;
import org.threads.task5.service.CurrencyManagement;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserIulia implements Runnable {

    private final CurrencyManagement currencyManagement;
    private static final Logger logger = Logger.getLogger(UserIulia.class.getName());

    private Amount amountToExchnge;
    private Currency targetCurrency;

    public UserIulia(CurrencyManagement currencyManagement, Amount amountToExchnge, Currency targetCurrency) {
        this.currencyManagement = currencyManagement;
        this.amountToExchnge = amountToExchnge;
        this.targetCurrency = targetCurrency;
    }


    @Override
    public void run() {
        //create example data
        Amount amount = new Amount(Currency.EUR, BigDecimal.valueOf(100));
        Amount amountExchanged = null;
        try {
            amountExchanged = currencyManagement.doExchange(2, this.amountToExchnge, this.targetCurrency);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        logger.log(Level.INFO, "amount exchanged is " + amountExchanged);
    }
}
