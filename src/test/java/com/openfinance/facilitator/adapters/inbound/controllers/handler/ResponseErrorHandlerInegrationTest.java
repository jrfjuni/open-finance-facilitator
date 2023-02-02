package com.openfinance.facilitator.adapters.inbound.controllers.handler;

import org.springframework.http.HttpMethod;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.ResponseActions;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;

public class ResponseErrorHandlerInegrationTest {

    protected ResponseActions createResponseActions(RestTemplate restTemplate, final String url, final HttpMethod httpMethod){
        return MockRestServiceServer.createServer(restTemplate)
                .expect(MockRestRequestMatchers.requestTo(url))
                .andExpect(method(httpMethod));
    }
}
