package com.openfinance.facilitator.adapters.constants;

public interface OpenFinanceFacilitatorBrazil {

   // EXTERNAL URL'S
   String PARTICIPANTS_EXTERNAL_URL = "https://data.directory.openbankingbrasil.org.br/participants";
   String DATA_DICTIONARY_EXTERNAL_URL = "https://openbanking-brasil.github.io/openapi/dictionary/%s.csv"; // Replace %s to csv file name

   // INTERNAL ENDPOINT
   String PARTICIPANTS_ENDPOINT = "/v1/open-finance-facilitator/participants";
   String DATA_DICTIONARY_ENDPOINT = "/v1/open-finance-facilitator/data-dictionary";
}
