package com.sh.commercechatbot.common.exception.chatbot;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChatbotErrorType {

    GPT_RESPONSE_ERROR(1);

    private int code;
}