package org.example;

public class InsufficientBalanceException extends AccountException{
    public InsufficientBalanceException(String message) {
        super(message);
    }
}
