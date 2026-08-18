package com.sh.commercechatbot.reviewChatbot.dto.request;

import com.sh.commercechatbot.enums.chatbot.request.ChatIntent;
import com.sh.commercechatbot.enums.chatbot.request.NextAction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter

@AllArgsConstructor
@NoArgsConstructor
public class ChatConversationState {
    private ChatIntent intent;
    private ResolvedProductDto product; // 사용자가 요청한 상품 추출
    private NextAction nexAction; // 다음 행동
}