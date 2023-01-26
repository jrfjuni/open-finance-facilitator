package com.openfinanceparticipants.adaperts.inbound.controllers;

import com.openfinanceparticipants.core.exceptions.OpenFinanceException;
import com.openfinanceparticipants.core.ports.OpenFinanceParticipantPort;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

@RestController
@RequestMapping("/v1/open-finance/participants")
public class OpenFinanceParticipantsController {

    @Autowired
    private OpenFinanceParticipantPort openFinanceParticipantPort;

    @Operation(summary = "Generate postman collection with data from OpenFinance Brazil participants.")
    @GetMapping(value = "/generate-postman-collection", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity generatePostmanCollection() throws OpenFinanceException, FileNotFoundException {
        final var  postmanCollectionFile  =  openFinanceParticipantPort.generatePostmanCollectionFile();
        final  var resource = new InputStreamResource(new FileInputStream(postmanCollectionFile));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + postmanCollectionFile.getName() + "\"")
                .contentLength(postmanCollectionFile.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
