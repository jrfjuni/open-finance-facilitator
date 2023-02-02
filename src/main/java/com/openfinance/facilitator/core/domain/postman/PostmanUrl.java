package com.openfinance.facilitator.core.domain.postman;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostmanUrl {

    private String raw;

    private String protocol;

    @JsonProperty("host")
    private String[] host;

    private String[] path;
}
