package com.openfinance.facilitator.adapters.inbound.controllers.handler;

import com.openfinance.facilitator.adapters.exception.OpenFinanceFacilitatorRestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.net.URI;

@Component
@Slf4j
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        HttpStatusCode status = response.getStatusCode();
        return status.is4xxClientError() || status.is5xxServerError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        final var responseAsString = response.getBody().toString();
        log.error("ResponseBody: {}", responseAsString);

        throw new OpenFinanceFacilitatorRestException(response.getStatusCode(), responseAsString);
    }

    @Override
    public void handleError(URI url, HttpMethod method, ClientHttpResponse response) throws IOException {
        final var responseAsString = response.getBody().toString();
        log.error("URL: {}, HttpMethod: {}, ResponseBody: {}", url, method, responseAsString);

        throw new OpenFinanceFacilitatorRestException(response.getStatusCode(), responseAsString);
    }
}
