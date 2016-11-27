package ru.bmstu.rsoi.web.exception;

/**
 * Created by ali on 22.11.16.
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
