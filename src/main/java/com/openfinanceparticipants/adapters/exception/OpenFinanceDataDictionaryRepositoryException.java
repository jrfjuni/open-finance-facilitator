package com.openfinanceparticipants.adapters.exception;

import com.openfinanceparticipants.core.exceptions.OpenFinanceRepositoryException;

public class OpenFinanceDataDictionaryRepositoryException extends OpenFinanceRepositoryException {

    public OpenFinanceDataDictionaryRepositoryException(final String message, final Throwable throwable){
        super(message, throwable);
    }
}
