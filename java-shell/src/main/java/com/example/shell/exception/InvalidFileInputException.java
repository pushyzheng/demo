package com.example.shell.exception;

/**
 * @author Pushy
 * @since 2020/6/7
 */
public class InvalidFileInputException extends RuntimeException {

    public InvalidFileInputException(String message) {
        super(message);
    }
}
