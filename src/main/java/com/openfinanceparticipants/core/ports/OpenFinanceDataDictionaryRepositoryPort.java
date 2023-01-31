package com.openfinanceparticipants.core.ports;

import com.openfinanceparticipants.core.domain.excel.ExcelModel;
import com.openfinanceparticipants.core.exceptions.OpenFinanceRepositoryException;

import java.nio.file.Path;
import java.util.List;

public interface OpenFinanceDataDictionaryRepositoryPort {

    void saveExcelFileInLocalDirectory(final List<ExcelModel> files) throws OpenFinanceRepositoryException;

    Path getPathToSaveFiles();

    Path createDirectoryToSaveFiles() throws OpenFinanceRepositoryException;
}
