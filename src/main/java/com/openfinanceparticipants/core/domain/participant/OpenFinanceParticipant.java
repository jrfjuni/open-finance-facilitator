package com.openfinanceparticipants.core.domain.participant;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.openfinanceparticipants.core.domain.participant.server.AuthorizationServer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class OpenFinanceParticipant {

    @JsonProperty("OrganisationId")
    private String organisationId;

    @JsonProperty("OrganisationName")
    private String organisationName;

    @JsonProperty("AuthorisationServers")
    private List<AuthorizationServer> servers;
}
