package com.openfinanceparticipants.adapters.inbound.controllers.v1;

import com.openfinanceparticipants.adapters.outbound.output.DataDirectoryOutput;
import com.openfinanceparticipants.core.exceptions.OpenFinanceException;
import com.openfinanceparticipants.core.ports.OpenFinanceDataDictionaryPort;
import com.openfinanceparticipants.core.ports.OpenFinanceDataDictionaryRepositoryPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/open-finance/data-dictionary")
@Slf4j
public class OpenFinanceDataDictionaryController {

    @Autowired
    private OpenFinanceDataDictionaryPort openFinanceDataDictionaryPort;

    @Autowired
    private OpenFinanceDataDictionaryRepositoryPort openFinanceDataDictionaryRepositoryPort;

    @PostMapping
    public ResponseEntity dataDictionary() throws OpenFinanceException {
       openFinanceDataDictionaryPort.findDataDictionaryConvertToExcelAndSave();
       final var path = openFinanceDataDictionaryRepositoryPort.getPathToSaveFiles();
       return ResponseEntity.ok().body(DataDirectoryOutput.of(path));
    }
}
