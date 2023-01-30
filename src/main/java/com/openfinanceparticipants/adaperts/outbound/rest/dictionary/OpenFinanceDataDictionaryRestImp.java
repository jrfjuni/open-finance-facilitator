package com.openfinanceparticipants.adaperts.outbound.rest.dictionary;

import com.openfinanceparticipants.adaperts.constants.OpenFinanceBrazil;
import com.openfinanceparticipants.core.ports.OpenFinanceDataDictionaryRestPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenFinanceDataDictionaryRestImp implements OpenFinanceDataDictionaryRestPort {

    @Autowired
    RestTemplate restTemplate;

    @Override
    public String getOpenFinanceDataDictionaryByFileName(final String fileName) {
        final var url = String.format(OpenFinanceBrazil.URL_DATA_DICTIONARY, fileName);
        final var response =
                 restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        return response.getBody();
    }
}
