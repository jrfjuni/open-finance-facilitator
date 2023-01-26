package com.openfinanceparticipants.adaperts.exception.handler;

import com.openfinanceparticipants.adaperts.exception.OpenFinanceParticipantsRestException;
import com.openfinanceparticipants.adaperts.exception.handler.model.ApiValidationError;
import com.openfinanceparticipants.core.exceptions.OpenFinanceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomRestExceptionHandler {

    private static final int INTERNAL_SERVER_ERROR = HttpStatus.INTERNAL_SERVER_ERROR.value();

    @ExceptionHandler(OpenFinanceParticipantsRestException.class)
    public ResponseEntity openFinanceParticipantsRestException(final OpenFinanceParticipantsRestException exception){
        final var error = new ApiValidationError(exception.getHttpStatusCode().value(), exception.getMessageErro());
        return ResponseEntity.status(exception.getHttpStatusCode()).body(error);
    }

    @ExceptionHandler(OpenFinanceException.class)
    public ResponseEntity openFinanceException(final OpenFinanceException openFinanceException){
        final var error = new ApiValidationError(INTERNAL_SERVER_ERROR, openFinanceException.getMessage());
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(error);
    }
}
