package com.openfinanceparticipants.adapters.inbound.controllers.v1;

import com.openfinanceparticipants.core.ports.OpenFinanceDataDictionaryPort;
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

    @PostMapping
    public ResponseEntity dataDictionary(){
        openFinanceDataDictionaryPort.dataDictionary();
        return ResponseEntity.ok().build();
    }
}
