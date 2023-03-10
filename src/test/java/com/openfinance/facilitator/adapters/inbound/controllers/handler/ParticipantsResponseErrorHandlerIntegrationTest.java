package com.openfinance.facilitator.adapters.inbound.controllers.handler;

import com.openfinance.facilitator.adapters.config.RestTemplateWithErrorHandlerConfig;
import com.openfinance.facilitator.adapters.constants.OpenFinanceFacilitatorBrazil;
import com.openfinance.facilitator.adapters.exception.OpenFinanceFacilitatorRestException;
import com.openfinance.facilitator.adapters.outbound.rest.participants.OpenFinanceParticipantRestImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ResponseActions;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withBadRequest;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RestTemplateWithErrorHandlerConfig.class, OpenFinanceParticipantRestImp.class})
public class ParticipantsResponseErrorHandlerIntegrationTest extends ResponseErrorHandlerInegrationTest {

    ResponseActions responseActions;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private OpenFinanceParticipantRestImp openFinanceParticipantRestImp;

    @BeforeEach
    public void setUp() {
        responseActions =
            createResponseActions(restTemplate, OpenFinanceFacilitatorBrazil.PARTICIPANTS_EXTERNAL_URL, HttpMethod.GET);
    }

    @Test
    public void when_participants_external_url_response_is4xx() {
        responseActions.andRespond(withBadRequest());
        assertThatThrownBy(() -> openFinanceParticipantRestImp.getAll())
                .hasCauseInstanceOf(OpenFinanceFacilitatorRestException.class);
    }

    @Test
    public void when_participants_external_url_response_is5xx() {
        responseActions.andRespond(withServerError());
        assertThatThrownBy(() -> openFinanceParticipantRestImp.getAll())
                .hasCauseInstanceOf(OpenFinanceFacilitatorRestException.class);
    }
}
