package org.threads.task5.models;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountBalance {
    private long userAccountId;
    private Currency currency;
    private BigDecimal balance;
}
