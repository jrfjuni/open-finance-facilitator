package com.openfinanceparticipants.adapters.outbound.rest.participants;

import com.openfinanceparticipants.adapters.constants.OpenFinanceBrazil;
import com.openfinanceparticipants.core.domain.participant.OpenFinanceParticipant;
import com.openfinanceparticipants.core.ports.OpenFinanceParticipantRestPort;
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
                            OpenFinanceBrazil.URL_PARTICIPANTS,
                            HttpMethod.GET, null,
                            new ParameterizedTypeReference<List<OpenFinanceParticipant>>() {});

            return response.getBody();
    }
}
