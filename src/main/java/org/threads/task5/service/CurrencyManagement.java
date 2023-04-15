package org.threads.task5.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.parser.ParseException;
import org.threads.task5.CustomExceptions.InputValidationException;
import org.threads.task5.dao.ExchangeRateDao;
import org.threads.task5.dao.UserAccountDao;
import org.threads.task5.models.AccountBalance;
import org.threads.task5.models.Amount;
import org.threads.task5.models.Currency;
import org.threads.task5.models.UserAccount;

public class CurrencyManagement {

    private ExchangeRateDao exchangedao;
    private UserAccountDao userAccountDao;
    private final static Logger logger= Logger.getLogger(CurrencyManagement.class.getName());

    public CurrencyManagement(final ExchangeRateDao exchangedao, final UserAccountDao userAccountDao) {
        this.exchangedao = exchangedao;
        this.userAccountDao = userAccountDao;
    }

    public Amount doExchange(long id, Amount amount1, Currency currency) throws IOException, ParseException {
        UserAccount account = userAccountDao.getById(id);
        logger.log(Level.INFO, "Found user account is " + account);
        BigDecimal rate = exchangedao.getExchangeRate(amount1.getCurrency(), currency, LocalDate.now());
        BigDecimal convertedAmount = rate.multiply(amount1.getAmount());
        synchronized (this) {
            account = userAccountDao.getById(id); //check again in case other thread already changed amount
            withdrawMoney(account, amount1);
            Amount amountConverted = new Amount(currency, convertedAmount);
            depositMoney(account, amountConverted);
            return amountConverted;
        }
    }
    private void withdrawMoney(UserAccount account, Amount amount) throws IOException, ParseException {
        AccountBalance deductedBalance = account.getAccountBalances()
                .stream()
                .filter(balance -> balance.getCurrency() == amount.getCurrency() && balance.getBalance()
                        .compareTo(amount.getAmount()) > 0)
                .map(balance -> balance.getBalance()
                        .subtract(amount.getAmount()))
                .map(balance -> new AccountBalance(account.getUserAccountId(), amount.getCurrency(), balance))
                .findAny()
                .orElseThrow(() -> new InputValidationException("balance does not have enough amount to convert or balance does not exist in given currency"));
        logger.log(Level.INFO,"deducted balance is "+deductedBalance);
        userAccountDao.setBalance( deductedBalance);
    }

    private void depositMoney(UserAccount account, Amount amountConverted) throws IOException, ParseException {
        AccountBalance addedBalance = account.getAccountBalances()
                .stream()
                .filter(bal -> bal.getCurrency() == amountConverted.getCurrency())
                .map(bal -> bal.getBalance()
                        .add(amountConverted.getAmount()))
                .map(bal -> new AccountBalance(account.getUserAccountId(), amountConverted.getCurrency(), bal))
                .findAny()
                .orElseThrow(() -> new InputValidationException("balance does not exist in target currency"));
        logger.log(Level.INFO,"added balance is "+addedBalance);
        userAccountDao.setBalance( addedBalance);
    }

    public List<UserAccount> getAccounts() throws IOException, ParseException {
        return userAccountDao.getAll();
    }
}
