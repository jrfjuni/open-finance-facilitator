package com.openfinance.facilitator.core.exceptions.excel;

import com.openfinance.facilitator.core.exceptions.OpenFinanceException;

public class OpenFinanceExcelException extends OpenFinanceException {

    public OpenFinanceExcelException(final String message, final Throwable throwable){
        super(message, throwable);
    }
}
