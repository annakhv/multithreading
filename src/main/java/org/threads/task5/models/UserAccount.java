package org.threads.task5.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserAccount {

    private String firstName;
    private String lastName;
    private String accountNumber;
    private List<AccountBalance> accountBalances;

}
