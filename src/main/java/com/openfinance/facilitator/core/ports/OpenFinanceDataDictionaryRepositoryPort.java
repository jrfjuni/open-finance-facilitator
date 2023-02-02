package com.openfinance.facilitator.core.ports;

import com.openfinance.facilitator.core.domain.excel.ExcelModel;
import com.openfinance.facilitator.core.exceptions.OpenFinanceDataDictionaryRepositoryException;

import java.util.List;

public interface OpenFinanceDataDictionaryRepositoryPort {
    String saveFiles(final List<ExcelModel> files, final String[] pathWhereToSaveFiles) throws OpenFinanceDataDictionaryRepositoryException;
}
