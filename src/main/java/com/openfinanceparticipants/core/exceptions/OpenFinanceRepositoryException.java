package com.openfinanceparticipants.core.exceptions;

public class OpenFinanceRepositoryException extends OpenFinanceException {

    public OpenFinanceRepositoryException(final String message, final Throwable throwable){
        super(message, throwable);
    }
}
