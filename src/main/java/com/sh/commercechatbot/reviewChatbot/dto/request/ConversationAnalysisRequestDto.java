package com.sh.commercechatbot.reviewChatbot.dto.request;

import com.sh.commercechatbot.enums.review.ConversationStep;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ConversationAnalysisRequestDto {
    // LLM이 판단을 할 수 있도록 도와주는 RequestDto -> Redis + CurrentMessage
    private ConversationStep step;
    private ResolvedProductDto product;
    private String currentMessage;

}
