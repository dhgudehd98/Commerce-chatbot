package com.sh.commercechatbot.reviewChatbot.dto.request;

import com.sh.commercechatbot.llm.enums.LlmModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatRequestDto {
    private Long selectedVariantId;
    private String currentMessage;
    private LlmModel llmModel;
}