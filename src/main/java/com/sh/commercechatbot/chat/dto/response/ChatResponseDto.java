package com.sh.commercechatbot.chat.dto.response;

import com.sh.commercechatbot.llm.dto.response.LlmChatResponseDto;
import com.sh.commercechatbot.llm.dto.response.LlmChatResponseError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatResponseDto {

    private String response;
    private LlmChatResponseError responseError;

    public ChatResponseDto(LlmChatResponseDto llmChatResponseDto) {
        this.response = llmChatResponseDto.getLlmResponse();
    }
}