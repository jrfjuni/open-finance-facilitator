package com.openfinance.facilitator.core.exceptions;

public class OpenFinanceDataDictionaryRepositoryException extends OpenFinanceException {

    public OpenFinanceDataDictionaryRepositoryException(final String message, final Throwable throwable){
        super(message, throwable);
    }

    public OpenFinanceDataDictionaryRepositoryException(final String message){
        super(message);
    }
}
