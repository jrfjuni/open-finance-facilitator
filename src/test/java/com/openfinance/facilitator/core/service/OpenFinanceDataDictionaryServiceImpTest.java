package com.openfinance.facilitator.core.service;

import com.openfinance.facilitator.OpenFinanceFacilitatorTest;
import com.openfinance.facilitator.core.domain.enums.EFileExtension;
import com.openfinance.facilitator.core.domain.excel.ExcelModel;
import com.openfinance.facilitator.core.domain.participant.OpenFinanceParticipant;
import com.openfinance.facilitator.core.exceptions.OpenFinanceException;
import com.openfinance.facilitator.core.exceptions.OpenFinanceDataDictionaryRepositoryException;
import com.openfinance.facilitator.core.exceptions.excel.OpenFinanceExcelException;
import com.openfinance.facilitator.core.ports.OpenFinanceDataDictionaryRepositoryPort;
import com.openfinance.facilitator.core.ports.OpenFinanceDataDictionaryRestPort;
import com.openfinance.facilitator.core.ports.OpenFinanceExcelPort;
import com.openfinance.facilitator.core.ports.OpenFinanceParticipantRestPort;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OpenFinanceDataDictionaryServiceImpTest extends OpenFinanceFacilitatorTest {

    @InjectMocks
    private OpenFinanceDataDictionaryServiceImp  openFinanceDataDictionaryServiceImp;

    @Mock
    private OpenFinanceParticipantRestPort participantRestPort;

    @Mock
    private OpenFinanceDataDictionaryRestPort dataDictionaryRestPort;

    @Mock
    private OpenFinanceExcelPort openFinanceExcelPort;

    @Mock
    private OpenFinanceDataDictionaryRepositoryPort openFinanceDataDictionaryRepositoryPort;

    private static final String WHERE_FILES_WERE_SAVED = "C:\\Users\\myUser\\AppData\\Local\\Temp\\openfinance-brazil-test\\files";

    @Test
    void when_findDataDictionaryConvertToExcelAndSave_success() throws IOException, OpenFinanceException {

        // Get Participants
        stepGetParticipants();

        // Download Data Dictionary
        stepDownloadDataDictionary();

        // Convert From Csv To Xlsx
        final var excelModelList = getExcelModelList();
        stepConverFromCsvToXlsx(excelModelList);

        // Save Excel File In Local Directory
        when(openFinanceDataDictionaryRepositoryPort.saveFiles(anyList(), any(String[].class)))
            .thenReturn(WHERE_FILES_WERE_SAVED);

        final var whereFilesWereSavedResponse = openFinanceDataDictionaryServiceImp.findDataDictionaryConvertToExcelAndSave();
        assertEquals(WHERE_FILES_WERE_SAVED, whereFilesWereSavedResponse);
    }

    @Test
    void when_findDataDictionaryConvertToExcelAndSave_saveError() throws IOException, OpenFinanceExcelException, OpenFinanceDataDictionaryRepositoryException {
        // Get Participants
        stepGetParticipants();

        // Download Data Dictionary
        stepDownloadDataDictionary();

        // Convert From Csv To Xlsx
        stepConverFromCsvToXlsx(getExcelModelList());

        // Error to save Excel File In Local Directory
        doThrow(OpenFinanceDataDictionaryRepositoryException.class)
                .when(openFinanceDataDictionaryRepositoryPort)
                    .saveFiles(anyList(), any(String[].class));

        assertThrows(OpenFinanceDataDictionaryRepositoryException.class, () -> openFinanceDataDictionaryServiceImp.findDataDictionaryConvertToExcelAndSave());
    }

    private List<ExcelModel> getExcelModelList(){
        return Arrays.asList(new ExcelModel("personal_accounts", EFileExtension.XLSX, new XSSFWorkbook()),
                new ExcelModel("loans", EFileExtension.XLSX, new XSSFWorkbook()),
                new ExcelModel("exchange", EFileExtension.XLSX, new XSSFWorkbook()));
    }

    private void stepGetParticipants() throws IOException {
        final var openFinanceParticipants = (List<OpenFinanceParticipant>)
                getResourceFileAsList("json/response/00-response-api-participants.json", OpenFinanceParticipant.class);
        when(participantRestPort.getAll()).thenReturn(openFinanceParticipants);
    }

    private void stepDownloadDataDictionary() throws IOException {
        final var dataDictionaryAsSytring = getResourceFileAsString("json/response/01-response-api-data-dictionary.csv");
        when(dataDictionaryRestPort.getOpenFinanceDataDictionaryByFileName(anyString()))
                .thenReturn(dataDictionaryAsSytring)
                .thenReturn(dataDictionaryAsSytring)
                .thenReturn(dataDictionaryAsSytring);
    }

    private void stepConverFromCsvToXlsx(final List<ExcelModel> excelModelList) throws OpenFinanceExcelException {
        when(openFinanceExcelPort.convertFromCsvToXlsx(anyList())).thenReturn(excelModelList);
    }
}
