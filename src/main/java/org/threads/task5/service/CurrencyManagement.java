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
        UserAccount account=userAccountDao.getById(id);
        logger.log(Level.INFO,"Found user account is "+account);
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
                .orElseThrow(() -> new InputValidationException("balance does not have enough amount to convert or balance does not exist in given currency"));
        logger.log(Level.INFO,"deducted balance is "+deductedBalance);
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
                .orElseThrow(() -> new InputValidationException("balance does not exist in target currency"));
        logger.log(Level.INFO,"added balance is "+addedBalance);
        userAccountDao.setBalance( deductedBalance);
        userAccountDao.setBalance( addedBalance);
        return new Amount(currency, convertedAmount);
    }

    public List<UserAccount> getAccounts() throws IOException, ParseException {
        return userAccountDao.getAll();
    }
}
