package com.openfinance.facilitator.core.exceptions;

public class OpenFinanceException extends Exception {

    public OpenFinanceException(final String message){
        super(message);
    }

    public OpenFinanceException(final String message, final Throwable throwable){
        super(message, throwable);
    }
}
