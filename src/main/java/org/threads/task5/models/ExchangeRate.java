package org.threads.task5.models;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExchangeRate {

    Currency from;
    Currency to;
    BigDecimal rate;
    LocalDate date;
}
