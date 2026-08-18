package com.sh.commercechatbot.llm.dto.request;

import com.sh.commercechatbot.reviewChatbot.dto.request.ChatRequestDto;
import com.sh.commercechatbot.llm.enums.LlmModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LlmChatRequestDto {
    private String request;
    private String prompt;
    private boolean useJson;
    private LlmModel llmModel;
//    private List<ChatMessageDto> messageList;


    public LlmChatRequestDto(ChatRequestDto chatRequestDto, String request, String systemPrompt) {
        this.request = request;
        this.prompt = systemPrompt;
        this.useJson = true;
        this.llmModel = chatRequestDto.getLlmModel();
    }
}