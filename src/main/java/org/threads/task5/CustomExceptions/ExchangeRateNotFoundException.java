package org.threads.task5.CustomExceptions;

public class ExchangeRateNotFoundException extends RuntimeException{
    public ExchangeRateNotFoundException(String message) {
        super(message);
    }
}
