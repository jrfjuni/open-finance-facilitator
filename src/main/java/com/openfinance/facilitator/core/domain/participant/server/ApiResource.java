package com.openfinance.facilitator.core.domain.participant.server;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResource {

    @JsonProperty("ApiFamilyType")
    private String apiFamilyType;

    @JsonProperty("ApiDiscoveryEndpoints")
    private List<ApiDiscoveryEndpoint> endpoints;
}
