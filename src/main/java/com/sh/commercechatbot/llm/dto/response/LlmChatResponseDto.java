package com.sh.commercechatbot.llm.dto.response;

import com.sh.commercechatbot.gpt.dto.response.GptResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LlmChatResponseDto {
    private String llmResponse;
    private LlmChatResponseError responseError;

    public LlmChatResponseDto(LlmChatResponseError responseError) {
        this.responseError = responseError;
    }

    public LlmChatResponseDto(String llmResponse) {
        this.llmResponse = llmResponse;
    }

    public static LlmChatResponseDto getLlmChatResponseDtoFromStream(GptResponseDto gptResponseDto) {
        return new LlmChatResponseDto(gptResponseDto.getSingleChoice().getDelta().getContent());
    }
}