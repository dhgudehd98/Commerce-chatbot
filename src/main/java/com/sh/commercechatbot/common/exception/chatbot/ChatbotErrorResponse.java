package com.sh.commercechatbot.common.exception.chatbot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ChatbotErrorResponse {
    private ChatbotError chatbotError;
}