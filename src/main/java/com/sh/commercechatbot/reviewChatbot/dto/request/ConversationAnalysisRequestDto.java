package com.sh.commercechatbot.reviewChatbot.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ConversationAnalysisRequestDto {
    private ChatConversationState state;
    private String currentMessage;

}