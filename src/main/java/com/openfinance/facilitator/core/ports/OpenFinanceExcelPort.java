package com.openfinance.facilitator.core.ports;

import com.openfinance.facilitator.core.domain.csv.Csv;
import com.openfinance.facilitator.core.domain.excel.ExcelModel;
import com.openfinance.facilitator.core.exceptions.excel.OpenFinanceExcelException;

import java.util.List;

public interface OpenFinanceExcelPort {
    List<ExcelModel> convertFromCsvToXlsx(final List<Csv> csvFiles) throws OpenFinanceExcelException;
}
