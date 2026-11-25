package com.sh.commercechatbot.reviewChatbot.dto.request;

import com.sh.commercechatbot.enums.review.ConversationStep;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatConversationState {
    /**
     * ChatConversation State 역할
     * - Redis에 저장할 데이터로, 현재 대화하고 있는 사용자의 대화정보를 저장한다.
     * - 사용자의 대화정보 + 사용자가 입력한 메세지를 바탕으로 LLM이 판단한다.(currentMessage)
     */
    private ConversationStep step;
    private ResolvedProductDto product; // 사용자가 요청한 상품 추출
    private List<ResolvedProductDto> productCandidates;


    public static ChatConversationState initial() {
        return new ChatConversationState(null, null, List.of());
    }
}
