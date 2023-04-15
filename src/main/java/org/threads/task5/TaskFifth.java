package org.threads.task5;

import org.threads.task5.dao.ExchangeRateDao;
import org.threads.task5.dao.UserAccountDao;
import org.threads.task5.dao.impl.DataManager;
import org.threads.task5.dao.impl.ExchangeRateImpl;
import org.threads.task5.dao.impl.UserAccountImpl;
import org.threads.task5.models.Amount;
import org.threads.task5.models.Currency;
import org.threads.task5.service.CurrencyManagement;

import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskFifth implements Runnable {
    public static void main(String[] args) {
             Thread thread=new Thread(new TaskFifth());
             thread.start();

    }

    @Override
    public void run() {
        // initialise required classes
        DataManager manager = new DataManager();
        ExchangeRateDao exchangeRateDao = new ExchangeRateImpl(manager);
        UserAccountDao userAccountDao = new UserAccountImpl(manager);
        CurrencyManagement currencyManagement = new CurrencyManagement(exchangeRateDao, userAccountDao);
        //create example data
        Amount amount = new Amount(Currency.USD, BigDecimal.valueOf(50));
        Amount amount1 = new Amount(Currency.USD, BigDecimal.valueOf(100));
        Amount amount2= new Amount(Currency.EUR, BigDecimal.valueOf(10));
        Amount amount3= new Amount(Currency.EUR, BigDecimal.valueOf(20));
        UserIulia iulia = new UserIulia(currencyManagement,amount,Currency.GEL);
        UserIulia iulia1 = new UserIulia(currencyManagement,amount1,Currency.GEL);
        UserIulia iulia2 = new UserIulia(currencyManagement,amount2,Currency.GEL);
        UserIulia iulia3 = new UserIulia(currencyManagement,amount3,Currency.GEL);
        ExecutorService executorService= Executors.newCachedThreadPool();
        //run tasks
        executorService.execute(iulia);
        executorService.execute(iulia1);
        executorService.execute(iulia2);
        executorService.execute(iulia3);

    }
}
