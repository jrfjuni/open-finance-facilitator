package com.openfinance.facilitator.core.domain.postman;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostmanSubItem {

    @JsonProperty("name")
    private String name;

    @JsonProperty("request")
    private PostmanRequest request;
}
