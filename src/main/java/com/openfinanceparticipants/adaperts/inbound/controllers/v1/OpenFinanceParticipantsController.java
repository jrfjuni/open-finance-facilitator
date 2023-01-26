package com.openfinanceparticipants.adaperts.inbound.controllers.v1;

import com.openfinanceparticipants.adaperts.inbound.controllers.handler.model.ApiValidationError;
import com.openfinanceparticipants.core.domain.postman.Postman;
import com.openfinanceparticipants.core.exceptions.OpenFinanceException;
import com.openfinanceparticipants.core.ports.OpenFinanceParticipantPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "File Downloaded.",
                    content = { @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE,
                                         schema = @Schema(implementation = Postman.class))}),
        @ApiResponse(responseCode = "4xx",
                     content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                        schema = @Schema(implementation = ApiValidationError.class))),
        @ApiResponse(responseCode = "5xx",
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = ApiValidationError.class))) })
    @GetMapping(value = "/generate-postman-collection",
                produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE, MediaType.APPLICATION_JSON_VALUE} )
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
