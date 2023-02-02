package com.openfinance.facilitator.adapters.inbound.controllers.v1;

import com.openfinance.facilitator.adapters.constants.OpenFinanceFacilitatorBrazil;
import com.openfinance.facilitator.adapters.outbound.output.DataDirectoryOutput;
import com.openfinance.facilitator.core.exceptions.OpenFinanceException;
import com.openfinance.facilitator.core.ports.OpenFinanceDataDictionaryPort;
import com.openfinance.facilitator.core.ports.OpenFinanceDataDictionaryRepositoryPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping(OpenFinanceFacilitatorBrazil.DATA_DICTIONARY_ENDPOINT)
@Slf4j
public class OpenFinanceDataDictionaryController {

    @Autowired
    private OpenFinanceDataDictionaryPort openFinanceDataDictionaryPort;

    @Autowired
    private OpenFinanceDataDictionaryRepositoryPort openFinanceDataDictionaryRepositoryPort;

    @PostMapping
    public ResponseEntity dataDictionary() throws OpenFinanceException {
       final var whereFilesWereSaved = openFinanceDataDictionaryPort.findDataDictionaryConvertToExcelAndSave();
       return ResponseEntity.ok().body(DataDirectoryOutput.of(whereFilesWereSaved));
    }
}
