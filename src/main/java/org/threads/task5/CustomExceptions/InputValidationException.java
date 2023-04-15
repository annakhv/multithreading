package org.threads.task5.CustomExceptions;

public class InputValidationException extends RuntimeException{

    public InputValidationException(String message) {
        super(message);
    }
}
