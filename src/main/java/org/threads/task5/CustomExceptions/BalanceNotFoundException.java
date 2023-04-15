package org.threads.task5.CustomExceptions;

public class BalanceNotFoundException extends RuntimeException{
    public BalanceNotFoundException(String message) {
        super(message);
    }
}
