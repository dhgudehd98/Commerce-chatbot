package com.sh.commercechatbot.gpt.dto.request;

import com.fasterxml.jackson.annotation.JsonValue;

public enum GptMessageRole {
    SYSTEM,
    USER,
    ASSISTANT;

    @JsonValue
    public String toValue() {
        return name().toLowerCase();
    }
}