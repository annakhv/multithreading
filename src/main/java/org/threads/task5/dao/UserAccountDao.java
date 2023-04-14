package org.threads.task5.dao;

import java.io.IOException;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.threads.task5.models.AccountBalance;
import org.threads.task5.models.UserAccount;

public interface UserAccountDao {

    List<UserAccount> getAll() throws IOException, ParseException;

    void setBalance(long userAccountId, AccountBalance balance);
}
