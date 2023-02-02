package com.openfinance.facilitator.adapters.inbound.controllers.handler;

import com.openfinance.facilitator.adapters.config.RestTemplateWithErrorHandlerConfig;
import com.openfinance.facilitator.adapters.constants.OpenFinanceFacilitatorBrazil;
import com.openfinance.facilitator.adapters.exception.OpenFinanceFacilitatorRestException;
import com.openfinance.facilitator.adapters.outbound.rest.dictionary.OpenFinanceDataDictionaryRestImp;
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
import static org.springframework.test.web.client.response.MockRestResponseCreators.withBadRequest;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RestTemplateWithErrorHandlerConfig.class, OpenFinanceDataDictionaryRestImp.class})
public class DictionaryResponseErrorHandlerInegrationTest extends ResponseErrorHandlerInegrationTest {

    ResponseActions responseActions;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private OpenFinanceDataDictionaryRestImp openFinanceDataDictionaryRestImp;

    private static final String FILE_NAME = "myFileNameTest";

    @BeforeEach
    public void setUp() {
        responseActions =
            createResponseActions(restTemplate, String.format(OpenFinanceFacilitatorBrazil.DATA_DICTIONARY_EXTERNAL_URL, FILE_NAME), HttpMethod.GET);
    }

    @Test
    public void when_dataDictionary_external_url_response_is4xx() {
        responseActions.andRespond(withBadRequest());
        assertThatThrownBy(() -> openFinanceDataDictionaryRestImp.getOpenFinanceDataDictionaryByFileName(FILE_NAME))
                .hasCauseInstanceOf(OpenFinanceFacilitatorRestException.class);
    }

    @Test
    public void when_dataDictionary_external_url_response_is5xx() {
        responseActions.andRespond(withServerError());
        assertThatThrownBy(() -> openFinanceDataDictionaryRestImp.getOpenFinanceDataDictionaryByFileName(FILE_NAME))
                .hasCauseInstanceOf(OpenFinanceFacilitatorRestException.class);
    }
}
