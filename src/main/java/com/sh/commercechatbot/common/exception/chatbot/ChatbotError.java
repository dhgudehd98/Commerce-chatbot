package com.sh.commercechatbot.common.exception.chatbot;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatbotError {

    private String errorCode;
    private String errorMessage;

    public ChatbotError(int errorCode, String errorMessage) {
        this.errorCode = String.valueOf(errorCode);
        this.errorMessage = errorMessage;
    }
}