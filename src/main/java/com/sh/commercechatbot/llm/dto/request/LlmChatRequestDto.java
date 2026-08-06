package com.sh.commercechatbot.llm.dto.request;

import com.sh.commercechatbot.chat.dto.request.ChatRequestDto;
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


    public
    LlmChatRequestDto(ChatRequestDto chatRequestDto, String systemPrompt) {
        this.request = chatRequestDto.getRequest();
        this.prompt = systemPrompt;
        this.useJson = false;
        this.llmModel = chatRequestDto.getLlmModel();
    }
}