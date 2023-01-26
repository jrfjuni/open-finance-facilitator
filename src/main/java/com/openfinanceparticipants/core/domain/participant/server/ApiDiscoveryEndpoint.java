package com.openfinanceparticipants.core.domain.participant.server;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiDiscoveryEndpoint {

    @JsonProperty("ApiEndpoint")
    private String endPoint;
}
