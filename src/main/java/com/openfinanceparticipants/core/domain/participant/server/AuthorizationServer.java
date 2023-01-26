package com.openfinanceparticipants.core.domain.participant.server;

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
public class AuthorizationServer {

    @JsonProperty("ApiResources")
    private List<ApiResource> resources;
}
