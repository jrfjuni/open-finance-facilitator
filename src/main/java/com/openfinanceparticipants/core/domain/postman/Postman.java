package com.openfinanceparticipants.core.domain.postman;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Postman {

    @JsonProperty("info")
    private PostmanInfo info;

    @JsonProperty("item")
    private List<PostmanItem> items;
}
