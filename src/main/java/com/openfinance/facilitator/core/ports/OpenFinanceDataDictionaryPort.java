package com.openfinance.facilitator.core.ports;

import com.openfinance.facilitator.core.exceptions.OpenFinanceException;

/**
 * Class responsible for excuting tasks related to the OpenFinance Brasil API's data dictionaries.
 * @date Jan 30, 2023
 * @version 1
 *
 */
public interface OpenFinanceDataDictionaryPort {
    String findDataDictionaryConvertToExcelAndSave() throws OpenFinanceException;
}
