package com.openfinanceparticipants.core.ports;

import com.openfinanceparticipants.core.domain.csv.Csv;
import com.openfinanceparticipants.core.domain.excel.ExcelModel;
import com.openfinanceparticipants.core.exceptions.excel.OpenFinanceExcelException;

import java.util.List;

public interface OpenFinanceExcelPort {
    List<ExcelModel> convertFromCsvToXlsx(final List<Csv> csvFiles) throws OpenFinanceExcelException;
}
