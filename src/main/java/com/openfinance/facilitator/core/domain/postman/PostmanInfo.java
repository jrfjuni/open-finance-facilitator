package com.openfinance.facilitator.core.domain.postman;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
public class PostmanInfo {

    @JsonProperty("_postman_id")
    private final String postmanId = UUID.randomUUID().toString();

    @JsonProperty("name")
    private final String name = "OpenFinance";

    @JsonProperty("schema")
    private final String schema = "https://schema.getpostman.com/json/collection/v2.1.0/collection.json";
}
