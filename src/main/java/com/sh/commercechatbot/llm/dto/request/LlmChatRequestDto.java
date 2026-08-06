package com.sh.commercechatbot.llm.dto.request;

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
    private List<ChatMessageDto> messageList;
}