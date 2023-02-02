package com.openfinance.facilitator.adapters.inbound.controllers.v1;

import com.openfinance.facilitator.adapters.constants.OpenFinanceFacilitatorBrazil;
import com.openfinance.facilitator.core.domain.postman.Postman;
import com.openfinance.facilitator.core.exceptions.OpenFinanceException;
import com.openfinance.facilitator.core.ports.OpenFinanceParticipantPort;
import com.openfinance.facilitator.adapters.inbound.controllers.handler.model.ApiValidationError;
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
@RequestMapping(OpenFinanceFacilitatorBrazil.PARTICIPANTS_ENDPOINT)
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
        final var resource = new InputStreamResource(new FileInputStream(postmanCollectionFile));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + postmanCollectionFile.getName() + "\"")
                .contentLength(postmanCollectionFile.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
