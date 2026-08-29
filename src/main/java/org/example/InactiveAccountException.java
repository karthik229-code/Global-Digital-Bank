package org.example;

public class InactiveAccountException extends AccountException{
    public InactiveAccountException(String message) {
        super(message);
    }
}
