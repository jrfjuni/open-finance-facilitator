package com.openfinance.facilitator.adapters.inbound.controllers.handler.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApiValidationError {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss a")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime timestamp;

    private int statusCode;

    private String messageError;

    private ApiValidationError(){
        this.timestamp = LocalDateTime.now();
    }

    public ApiValidationError(final int statusCode, final String messageError){
        this();
        this.statusCode = statusCode;
        this.messageError = messageError;
    }
}
