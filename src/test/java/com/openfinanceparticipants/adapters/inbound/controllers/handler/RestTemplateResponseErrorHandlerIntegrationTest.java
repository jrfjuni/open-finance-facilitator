package com.openfinanceparticipants.adapters.inbound.controllers.handler;

import com.openfinanceparticipants.adapters.config.RestTemplateWithErrorHandlerConfig;
import com.openfinanceparticipants.adapters.constants.OpenFinanceBrazil;
import com.openfinanceparticipants.adapters.exception.OpenFinanceParticipantsRestException;
import com.openfinanceparticipants.adapters.outbound.rest.OpenFinanceParticipantRestImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.ResponseActions;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withBadRequest;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RestTemplateWithErrorHandlerConfig.class, OpenFinanceParticipantRestImp.class})
public class RestTemplateResponseErrorHandlerIntegrationTest {

    private ResponseActions responseActions;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private OpenFinanceParticipantRestImp openFinanceParticipantRestImp;

    @BeforeEach
    public void setUp() {
        responseActions = MockRestServiceServer.createServer(restTemplate)
                .expect(requestTo(OpenFinanceBrazil.URL_PARTICIPANTS))
                .andExpect(method(HttpMethod.GET));
    }

    @Test
    public void when_response4xx() {
        responseActions.andRespond(withBadRequest());
        assertThatThrownBy(() -> openFinanceParticipantRestImp.getAll())
                .hasCauseInstanceOf(OpenFinanceParticipantsRestException.class);
    }

    @Test
    public void when_response5xx() {
        responseActions.andRespond(withServerError());
        assertThatThrownBy(() -> openFinanceParticipantRestImp.getAll())
                .hasCauseInstanceOf(OpenFinanceParticipantsRestException.class);
    }
}
