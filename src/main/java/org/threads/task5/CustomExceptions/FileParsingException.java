package org.threads.task5.CustomExceptions;

public class FileParsingException extends RuntimeException{
    public FileParsingException(String message) {
        super(message);
    }
}
