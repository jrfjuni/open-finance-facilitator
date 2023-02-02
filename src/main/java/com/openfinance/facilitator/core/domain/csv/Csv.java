package com.openfinance.facilitator.core.domain.csv;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Csv {

    private String name;

    private String content;
}
