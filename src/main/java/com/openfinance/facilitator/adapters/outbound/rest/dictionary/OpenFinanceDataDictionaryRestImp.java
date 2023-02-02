package com.openfinance.facilitator.adapters.outbound.rest.dictionary;

import com.openfinance.facilitator.adapters.constants.OpenFinanceFacilitatorBrazil;
import com.openfinance.facilitator.core.ports.OpenFinanceDataDictionaryRestPort;
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
        final var url = String.format(OpenFinanceFacilitatorBrazil.DATA_DICTIONARY_EXTERNAL_URL, fileName);
        final var response =
                 restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        return response.getBody();
    }
}
