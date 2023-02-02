package com.openfinance.facilitator.adapters.outbound.repository;

import com.openfinance.facilitator.adapters.exception.OpenFinanceLocalRepositoryException;
import com.openfinance.facilitator.core.domain.enums.EFileExtension;
import com.openfinance.facilitator.core.domain.excel.ExcelModel;
import com.openfinance.facilitator.core.exceptions.OpenFinanceDataDictionaryRepositoryException;
import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class OpenFinanceLocalRepositoryTest {

    @InjectMocks
    private OpenFinanceLocalRepository openFinanceLocalRepository;

    private static final String[] PATH = new String[]{"openfinance-brazil-test", "files"};

    @Test
    void when_saveFiles_than_success() throws OpenFinanceDataDictionaryRepositoryException, IOException {
        final var whereWereSaved = openFinanceLocalRepository.saveFiles(getExcelModelList(), PATH);
        assertTrue(whereWereSaved.contains(String.join("\\", PATH)));
        deleteDirectory(whereWereSaved);
    }

    @Test
    void when_saveFiles_withNullFolders_than_exception() {
        assertThrows(OpenFinanceLocalRepositoryException.class,
                () -> openFinanceLocalRepository.saveFiles(getExcelModelList(), null), "Folders can't be null.");
    }

    @Test
    void when_saveFiles_withInvalidFolder_than_exception() {
        assertThrows(OpenFinanceLocalRepositoryException.class,
                () -> openFinanceLocalRepository.saveFiles(getExcelModelList(), "/"));
    }

    private List<ExcelModel> getExcelModelList(){
        return Arrays.asList(new ExcelModel("personal_accounts", EFileExtension.XLSX, new XSSFWorkbook()),
                new ExcelModel("loans", EFileExtension.XLSX, new XSSFWorkbook()),
                new ExcelModel("exchange", EFileExtension.XLSX, new XSSFWorkbook()));
    }

    void deleteDirectory(final String whereWereSaved) throws IOException {
        var pathToSaveFiles =  Paths.get(whereWereSaved).toFile();
        FileUtils.cleanDirectory(pathToSaveFiles);
        FileUtils.deleteDirectory(pathToSaveFiles);
    }
}
