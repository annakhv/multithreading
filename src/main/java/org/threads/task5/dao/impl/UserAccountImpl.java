package org.threads.task5.dao.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.threads.task5.dao.UserAccountDao;
import org.threads.task5.models.AccountBalance;
import org.threads.task5.models.UserAccount;

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
        return getAll().stream().filter(account->account.getUserAccountId()==id)
                .findAny().orElseThrow(()->new RuntimeException("no account found with the id "+id));
    }
    @Override
    public void setBalance(final AccountBalance balance) throws IOException, ParseException {
       var account=getAll().stream()
                .filter(userAccount->userAccount.getUserAccountId()==balance.getUserAcountId())
                .map(userAccount-> {
                    for (int i = 0; i < userAccount.getAccountBalances().size(); i++) {
                        if (userAccount.getAccountBalances().get(i).getCurrency() == balance.getCurrency()) {

                            userAccount.getAccountBalances().set(i, balance);

                        }
                    }
                    return userAccount;
                }).findAny().orElseThrow(()->new RuntimeException("new balance could not be set because this user account does not exist"));
        parser.writeNewBalance(account);
    }



}
