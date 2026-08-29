package org.example;

public class InvalidAmountException extends AccountException {
    public InvalidAmountException(String message) {
        super(message);
    }
}
