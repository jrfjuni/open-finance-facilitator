package com.openfinance.facilitator.core.service;

import com.openfinance.facilitator.core.domain.participant.OpenFinanceParticipant;
import com.openfinance.facilitator.core.exceptions.OpenFinanceException;
import com.openfinance.facilitator.core.ports.OpenFinanceParticipantRestPort;
import com.openfinance.facilitator.OpenFinanceFacilitatorTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OpenFinanceParticipantServiceImpTest extends OpenFinanceFacilitatorTest {

    @InjectMocks
    OpenFinanceParticipantServiceImp openFinanceParticipantService;

    @Mock
    OpenFinanceParticipantRestPort openFinanceParticipantRestPort;

    @Test
    void when_generatePostmanCollectionFile_success() throws IOException, OpenFinanceException {
        final var openFinanceParticipants = (List<OpenFinanceParticipant>)
                getResourceFileAsList("json/response/00-response-api-participants.json", OpenFinanceParticipant.class);

        when(openFinanceParticipantRestPort.getAll()).thenReturn(openFinanceParticipants);

        final var postmanCollectionFile = openFinanceParticipantService.generatePostmanCollectionFile();
        assertNotNull(postmanCollectionFile);
        deleteFile(postmanCollectionFile);
    }
}
