package org.threads.task5.dao;

import java.util.List;

import org.threads.task5.models.AccountBalance;
import org.threads.task5.models.UserAccount;

public interface UserAccountDao {

    List<UserAccount> getAll();

    void setBalance(long userAccountId, AccountBalance balance);
}
