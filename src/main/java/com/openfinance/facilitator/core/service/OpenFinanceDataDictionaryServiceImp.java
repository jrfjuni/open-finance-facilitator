package com.openfinance.facilitator.core.service;

import com.openfinance.facilitator.core.constants.OpenFinanceCoreConstants;
import com.openfinance.facilitator.core.domain.csv.Csv;
import com.openfinance.facilitator.core.domain.excel.ExcelModel;
import com.openfinance.facilitator.core.ports.*;
import com.openfinance.facilitator.core.domain.participant.server.ApiDiscoveryEndpoint;
import com.openfinance.facilitator.core.exceptions.OpenFinanceException;
import com.openfinance.facilitator.core.exceptions.OpenFinanceDataDictionaryRepositoryException;
import com.openfinance.facilitator.core.exceptions.excel.OpenFinanceExcelException;
import com.openfinance.facilitator.core.utils.OpenFinanceUrlUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
public class OpenFinanceDataDictionaryServiceImp implements OpenFinanceDataDictionaryPort {

    @Autowired
    private OpenFinanceParticipantRestPort participantRestPort;

    @Autowired
    private OpenFinanceDataDictionaryRestPort dataDictionaryRestPort;

    @Autowired
    private OpenFinanceExcelPort openFinanceExcelPort;

    @Autowired
    private OpenFinanceDataDictionaryRepositoryPort openFinanceDataDictionaryRepositoryPort;

    /**
     * Method responsible to finding, converting from .csv to .xlsx and saving the data dictionaries.
     * @return Path where the files were saved.
     * @throws OpenFinanceException
     */
    @Override
    public String findDataDictionaryConvertToExcelAndSave() throws OpenFinanceException {
        final var participants = participantRestPort.getAll();

        // Using Set List to  not contain duplicate values.
        Set<String> pathList = new HashSet<>();

        participants.forEach(participant ->
            participant.getServers().forEach(server ->
                server.getResources().forEach(resource ->
                    // The URL path (ex: /products-services/v1/personal-accounts) is default for all participants.
                    resource.getEndpoints().forEach(endpoint -> formatAndAddPathToSetList(endpoint, pathList)))));

        final var csvList = downloadFiles(pathList);
        return convertToExcelAndSave(csvList);
    }

    private String convertToExcelAndSave(final List<Csv> csvList) throws OpenFinanceException {

        try {
            final List<ExcelModel> excelModelList = openFinanceExcelPort.convertFromCsvToXlsx(csvList);
            return openFinanceDataDictionaryRepositoryPort.saveFiles(excelModelList, getFoldersWhereFilesWillBeSaved());
        } catch (final OpenFinanceExcelException e) {
            log.error("Error to convert from .csv to .xlsx: {}", e.getMessage());
            throw e;
        } catch (final OpenFinanceDataDictionaryRepositoryException e){
            log.error("Error to save files: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * The data dictionary URL uses as the CSV File name (without param) of the OpenFinance Brazil urls.
     *
     * Exemple:
     *   Personal Accounts URL: GET /products-services/v1/personal-accounts
     *   Personal Accounts data dictionary URL: https://openbanking-brasil.github.io/openapi/dictionary/personal_accounts.csv
     *
     * This method finds the last path of OpenFinance URL and formats it to be used as the file name of data dictionary url.
     *
     * @see 'https://openfinancebrasil.atlassian.net/wiki/spaces/OF/pages/17367790/Dados+Abertos'
     * @param discoveryEndpoint
     * @param setList
     */
    private void formatAndAddPathToSetList(final ApiDiscoveryEndpoint discoveryEndpoint, Set<String> setList){
        try {
            final var paths = OpenFinanceUrlUtils.getPathByURL(discoveryEndpoint.getEndPoint());
            final var pathToDictionary = OpenFinanceUrlUtils.getLastPathWithoutParam(paths).replaceAll("-", "_");
            setList.add(pathToDictionary);
        }  catch (final OpenFinanceException ex){
            log.error("Error to format path: {} {}", ex.getMessage(), discoveryEndpoint.getEndPoint());
        }
    }

    /**
     * <p>Download data dictionary (CSV file) by path.</p>
     * @param filePaths
     * @return List of CSV files.
     */
    private List<Csv> downloadFiles(final Set<String> filePaths) {
        List<Csv> csvFiles = new ArrayList<>();

        filePaths.forEach(path -> {
            try {
                final var csvAsString = dataDictionaryRestPort.getOpenFinanceDataDictionaryByFileName(path);
                csvFiles.add(Csv.builder()
                                .name(path)
                                .content(csvAsString)
                                .build());
            }catch (final Throwable  t){
                log.error("File download error: {} {}", t.getMessage(), path);
            }
        });

        return csvFiles;
    }

    private String[] getFoldersWhereFilesWillBeSaved(){
        final var basePath =  OpenFinanceCoreConstants.BASE_PATH_TO_SAVE_DT_DICTIONARY_FILES;
        var newBasePath  =  new String[basePath.length + 1];

        for(int i = 0; i < basePath.length; i++)
            newBasePath[i] = basePath[i];

        newBasePath[basePath.length] = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        return newBasePath;
    }
}
