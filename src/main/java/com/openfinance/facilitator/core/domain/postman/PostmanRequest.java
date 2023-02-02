package com.openfinance.facilitator.core.domain.postman;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.http.HttpMethod;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostmanRequest {

    @JsonProperty("method")
    private final String method = HttpMethod.GET.name();

    @JsonProperty("header")
    private String[] header = new String[0];

    @JsonProperty("url")
    private PostmanUrl url;

    @JsonProperty("description")
    private String description;
}
