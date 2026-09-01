package com.sh.commercechatbot.common.exception.chatbot;

public class ChatbotException extends RuntimeException{
    private final ChatbotErrorType chatbotErrorType;

    public ChatbotException(String message, ChatbotErrorType chatbotErrorType) {
        super(message);
        this.chatbotErrorType = chatbotErrorType;
    }

    public ChatbotErrorType getChatbotErrorType() {
        return this.chatbotErrorType;
    }



    @Override
    public String getMessage() {
        return "";
    }
}