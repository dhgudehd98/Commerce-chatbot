package com.sh.commercechatbot.reviewChatbot.dto.response;

import com.sh.commercechatbot.common.exception.chatbot.ChatbotError;
import com.sh.commercechatbot.common.exception.chatbot.ChatbotErrorResponse;
import com.sh.commercechatbot.llm.dto.response.LlmChatResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatResponseDto {

    private String response;
    private ChatbotError error;

    public ChatResponseDto(LlmChatResponseDto llmChatResponseDto) {
        this.response = llmChatResponseDto.getLlmResponse();
        this.error = llmChatResponseDto.getError();
    }
}