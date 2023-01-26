package com.openfinanceparticipants.core.domain.enums;

import lombok.Getter;

@Getter
public enum EFileExtension {

    JSON(".json");

    private String value;

    EFileExtension(final String value){
        this.value = value;
    }
}
