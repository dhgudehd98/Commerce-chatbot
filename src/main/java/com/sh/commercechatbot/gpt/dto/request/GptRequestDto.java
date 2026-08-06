package com.sh.commercechatbot.gpt.dto.request;

import com.sh.commercechatbot.gpt.dto.response.GptResponseFormat;
import com.sh.commercechatbot.llm.dto.request.LlmChatRequestDto;
import com.sh.commercechatbot.llm.enums.LlmModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GptRequestDto {
    private List<GptRequestCompletionDto> messages;
    private LlmModel model;
    private Boolean stream;
    private GptResponseFormat response_format;

    public GptRequestDto(LlmChatRequestDto llmChatRequestDto) {
        this.messages = new ArrayList<>();
        this.messages.add(new GptRequestCompletionDto(GptMessageRole.SYSTEM, llmChatRequestDto.getPrompt()));
        this.messages.add(new GptRequestCompletionDto(GptMessageRole.USER, llmChatRequestDto.getRequest()));
        this.stream = true;
        this.model = llmChatRequestDto.getLlmModel();
    }
}