package com.sh.commercechatbot.chat.dto.request;

import com.sh.commercechatbot.llm.enums.LlmModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatRequestDto {
    private String request;
    private LlmModel llmModel;
}