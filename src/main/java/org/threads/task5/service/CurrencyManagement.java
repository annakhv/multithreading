package org.threads.task5.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.threads.task5.dao.ExchangeRateDao;
import org.threads.task5.dao.UserAccountDao;
import org.threads.task5.models.AccountBalance;
import org.threads.task5.models.Amount;
import org.threads.task5.models.Currency;
import org.threads.task5.models.UserAccount;

public class CurrencyManagement {

    private ExchangeRateDao exchangedao;
    private UserAccountDao userAccountDao;

    public CurrencyManagement(final ExchangeRateDao exchangedao, final UserAccountDao userAccountDao) {
        this.exchangedao = exchangedao;
        this.userAccountDao = userAccountDao;
    }

    public Amount doExchange(UserAccount account, Amount amount1, Currency currency) {
        BigDecimal rate = exchangedao.getExchangeRate(amount1.getCurrency(), currency, LocalDate.now());
        //withdraw money
        //check that user has balance in given currency & check that users balance is positive after deducting the amount
        AccountBalance deductedBalance = account.getAccountBalances()
                .stream()
                .filter(balance -> balance.getCurrency() == amount1.getCurrency() && balance.getBalance()
                        .compareTo(amount1.getAmount()) > 0)
                .map(balance -> balance.getBalance()
                        .subtract(amount1.getAmount()))
                .map(balance -> new AccountBalance(account.getUserAccountId(), amount1.getCurrency(), balance))
                .findAny()
                .orElseThrow(() -> new RuntimeException("amount is not enough of no balance with this currency"));
        //do conversion
        BigDecimal convertedAmount = rate.multiply(amount1.getAmount());
        //add money to relevant balance account
        AccountBalance addedBalance = account.getAccountBalances()
                .stream()
                .filter(bal -> bal.getCurrency() == currency)
                .map(bal -> bal.getBalance()
                        .add(convertedAmount))
                .map(bal -> new AccountBalance(account.getUserAccountId(), currency, bal))
                .findAny()
                .orElseThrow(() -> new RuntimeException("balance does not exist in target currency"));
        userAccountDao.setBalance(account.getUserAccountId(), deductedBalance);
        userAccountDao.setBalance(account.getUserAccountId(), addedBalance);
        return new Amount(currency, convertedAmount);
    }

    public List<UserAccount> getAccounts() throws IOException, ParseException {
        return userAccountDao.getAll();
    }
}
