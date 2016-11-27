package ru.bmstu.rsoi.web.exception;

/**
 * Created by ali on 22.11.16.
 */
public class ImpossibleOperationException extends RuntimeException {
    public ImpossibleOperationException(String message) {
        super(message);
    }
}
