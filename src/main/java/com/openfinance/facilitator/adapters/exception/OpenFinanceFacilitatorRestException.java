package com.openfinance.facilitator.adapters.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatusCode;

import java.io.IOException;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OpenFinanceFacilitatorRestException extends IOException {

    private HttpStatusCode httpStatusCode;

    private String messageErro;
}
