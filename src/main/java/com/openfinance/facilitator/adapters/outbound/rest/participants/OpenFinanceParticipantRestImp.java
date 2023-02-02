package com.openfinance.facilitator.adapters.outbound.rest.participants;

import com.openfinance.facilitator.adapters.constants.OpenFinanceFacilitatorBrazil;
import com.openfinance.facilitator.core.domain.participant.OpenFinanceParticipant;
import com.openfinance.facilitator.core.ports.OpenFinanceParticipantRestPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class OpenFinanceParticipantRestImp implements OpenFinanceParticipantRestPort {

    @Autowired
    RestTemplate restTemplate;

    public List<OpenFinanceParticipant> getAll(){
        final var response=
            restTemplate.exchange(
                OpenFinanceFacilitatorBrazil.PARTICIPANTS_EXTERNAL_URL,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<OpenFinanceParticipant>>() {});

        return response.getBody();
    }
}
