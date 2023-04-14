package org.threads.task5.dao.impl;

import java.util.List;

import org.threads.task5.dao.UserAccountDao;
import org.threads.task5.models.AccountBalance;
import org.threads.task5.models.UserAccount;

public class UserAccountImpl implements UserAccountDao {
    @Override
    public List<UserAccount> getAll() {

        return null;
    }

    @Override
    public void setBalance(final long userAccountId, final AccountBalance balance) {

    }
}
