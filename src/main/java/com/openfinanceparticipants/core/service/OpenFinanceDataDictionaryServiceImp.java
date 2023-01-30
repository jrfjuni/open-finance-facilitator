package com.openfinanceparticipants.core.service;

import com.openfinanceparticipants.core.domain.participant.server.ApiDiscoveryEndpoint;
import com.openfinanceparticipants.core.exceptions.OpenFinanceException;
import com.openfinanceparticipants.core.ports.OpenFinanceDataDictionaryPort;
import com.openfinanceparticipants.core.ports.OpenFinanceDataDictionaryRestPort;
import com.openfinanceparticipants.core.ports.OpenFinanceParticipantRestPort;
import com.openfinanceparticipants.core.utils.OpenFinanceUrlUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class OpenFinanceDataDictionaryServiceImp implements OpenFinanceDataDictionaryPort {

    @Autowired
    private OpenFinanceParticipantRestPort participantRestPort;

    @Autowired
    private OpenFinanceDataDictionaryRestPort dataDictionaryRestPort;

    @Override
    public void dataDictionary(){
        final var participants = participantRestPort.getAll();

        // Using Set List to  not contain duplicate values.
        Set<String> pathList = new HashSet<>();

        participants.forEach(participant ->
            participant.getServers().forEach(server ->
                server.getResources().forEach(resource ->
                    // The URL path (ex: /products-services/v1/personal-accounts) is default for all participants.
                    resource.getEndpoints().forEach(endpoint -> formatAndAddPathToSetList(endpoint, pathList)))));

        final var csvFiles = downloadFiles(pathList);
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
    private List<String> downloadFiles(final Set<String> filePaths) {
        List<String> csvFiles = new ArrayList<>();

        filePaths.forEach(path -> {
            try {
                final var csvAsString = dataDictionaryRestPort.getOpenFinanceDataDictionaryByFileName(path);
                csvFiles.add(csvAsString);
            }catch (final Throwable  t){
                log.error("File download error: {} {}", t.getMessage(), path);
            }
        });

        return csvFiles;
    }
}
