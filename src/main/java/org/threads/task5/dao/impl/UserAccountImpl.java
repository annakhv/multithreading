package org.threads.task5.dao.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.threads.task5.dao.UserAccountDao;
import org.threads.task5.models.AccountBalance;
import org.threads.task5.models.UserAccount;

public class UserAccountImpl implements UserAccountDao {

    private DataParser parser;

    public UserAccountImpl(final DataParser parser) {
        this.parser = parser;
    }

    @Override
    public List<UserAccount> getAll() throws IOException, ParseException {
        List<String> list = List.of("Iulia.json", "Jane.json", "maria.json", "tom.json");
        List<UserAccount> accounts = new ArrayList<>();
        for (String name : list) {
            UserAccount account = parser.parseUserFile(name);
            accounts.add(account);
        }

        return accounts;
    }

    @Override
    public void setBalance(final long userAccountId, final AccountBalance balance) {

    }



}
