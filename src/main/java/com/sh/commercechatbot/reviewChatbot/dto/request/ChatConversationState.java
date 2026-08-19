package com.sh.commercechatbot.reviewChatbot.dto.request;

import com.sh.commercechatbot.enums.chatbot.request.ChatIntent;
import com.sh.commercechatbot.enums.chatbot.request.NextAction;
import com.sh.commercechatbot.redis.chat.ChatConversationRepositoryInRedis;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatConversationState {
    private ChatIntent intent;
    private ResolvedProductDto product; // 사용자가 요청한 상품 추출
    private List<ResolvedProductDto> productCandidates;
    private NextAction nextAction; // 다음 행동

    @Override
    public String toString() {
        return "ChatConversationState{" +
                "intent=" + intent +
                ", product=" + product +
                ", nexAction=" + nextAction +
                '}';
    }

    public static ChatConversationState initial() {
        return new ChatConversationState(null, null, null, null);
    }
}