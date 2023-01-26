package com.openfinanceparticipants.adaperts.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatusCode;

import java.io.IOException;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OpenFinanceParticipantsRestException extends IOException {

    private HttpStatusCode httpStatusCode;

    private String messageErro;
}
