package com.openfinance.facilitator.adapters.exception;

import com.openfinance.facilitator.core.exceptions.OpenFinanceDataDictionaryRepositoryException;

public class OpenFinanceLocalRepositoryException extends OpenFinanceDataDictionaryRepositoryException {

    public OpenFinanceLocalRepositoryException(final String message, final Throwable throwable){
        super(message, throwable);
    }

    public OpenFinanceLocalRepositoryException(final String message){
        super(message);
    }
}
