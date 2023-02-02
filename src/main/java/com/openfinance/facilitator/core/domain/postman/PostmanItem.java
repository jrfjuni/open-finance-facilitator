package com.openfinance.facilitator.core.domain.postman;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostmanItem {

    @JsonProperty("name")
    private String name;

    @JsonProperty("item")
    private List<PostmanSubItem> subItems;
}
