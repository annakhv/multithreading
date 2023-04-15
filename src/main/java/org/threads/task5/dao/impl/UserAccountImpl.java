package org.threads.task5.dao.impl;

import org.json.simple.parser.ParseException;
import org.threads.task5.CustomExceptions.AccountNotFoundException;
import org.threads.task5.CustomExceptions.BalanceNotFoundException;
import org.threads.task5.dao.UserAccountDao;
import org.threads.task5.models.AccountBalance;
import org.threads.task5.models.UserAccount;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserAccountImpl implements UserAccountDao {

    private DataManager parser;

    public UserAccountImpl(final DataManager parser) {
        this.parser = parser;
    }

    @Override
    public List<UserAccount> getAll() throws IOException, ParseException {
        List<String> list = List.of("Iulia.json", "Jane.json", "Maria.json", "Tom.json");
        List<UserAccount> accounts = new ArrayList<>();
        for (String name : list) {
            UserAccount account = parser.parseUserFile(name);
            accounts.add(account);
        }

        return accounts;
    }

    public UserAccount getById(long id) throws IOException, ParseException {
        return getAll().stream().filter(account -> account.getUserAccountId() == id)
                .findAny().orElseThrow(() -> new AccountNotFoundException("no account found with the id " + id));
    }

    @Override
    public void setBalance(final AccountBalance balance) throws IOException, ParseException {
        var account = getAll().stream()
                .filter(userAccount -> userAccount.getUserAccountId() == balance.getUserAccountId())
                .map(userAccount -> {
                    List<AccountBalance> userBalances = userAccount.getAccountBalances().stream().map(bal -> {
                        if (bal.getCurrency() == balance.getCurrency()) {
                            return balance;
                        }
                        return bal;
                    }).toList();
                    if (userBalances.contains(balance)) {
                        userAccount.setAccountBalances(userBalances);
                        return userAccount;
                    } else {
                        throw new BalanceNotFoundException("user does not have balance with currency " + balance.getCurrency());
                    }

                }).findAny().orElseThrow(() -> new AccountNotFoundException("new balance could not be set because user account with id " + balance.getUserAccountId() + " does not exist"));
        parser.writeNewBalance(account);
    }


}
