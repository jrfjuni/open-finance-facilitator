package com.openfinanceparticipants.core.exceptions.excel;

import com.openfinanceparticipants.core.exceptions.OpenFinanceException;

public class OpenFinanceExcelException extends OpenFinanceException {

    public OpenFinanceExcelException(final String message, final Throwable throwable){
        super(message, throwable);
    }
}
