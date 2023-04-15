package org.threads.task5;

import org.json.simple.parser.ParseException;
import org.threads.task5.dao.ExchangeRateDao;
import org.threads.task5.dao.UserAccountDao;
import org.threads.task5.dao.impl.DataManager;
import org.threads.task5.dao.impl.ExchangeRateImpl;
import org.threads.task5.dao.impl.UserAccountImpl;
import org.threads.task5.models.Amount;
import org.threads.task5.models.Currency;
import org.threads.task5.service.CurrencyManagement;

import java.io.IOException;
import java.math.BigDecimal;

public class taskFifth implements Runnable{

    private CurrencyManagement currencyManagement;

    public taskFifth(CurrencyManagement currencyManagement) {
        this.currencyManagement = currencyManagement;
    }

    @Override
    public void run() {

    }


    public static void main(String[] args) throws IOException, ParseException {
        // initialise required classes
        DataManager manager = new DataManager();
        ExchangeRateDao exchangeRateDao = new ExchangeRateImpl(manager);
        UserAccountDao userAccountDao = new UserAccountImpl(manager);
        CurrencyManagement currencyManagement = new CurrencyManagement(exchangeRateDao, userAccountDao);
        taskFifth fifth = new taskFifth(currencyManagement);
        //create example data
        Amount amount=new Amount(Currency.EUR, BigDecimal.valueOf(100));
        Amount amountExchanged=currencyManagement.doExchange(2,amount,Currency.GEL);
        System.out.println(amountExchanged);


    }
}
