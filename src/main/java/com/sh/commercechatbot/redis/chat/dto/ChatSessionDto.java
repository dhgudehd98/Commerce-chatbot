package com.sh.commercechatbot.redis.chat.dto;

import com.sh.commercechatbot.enums.review.ConversationStep;
import com.sh.commercechatbot.review.dto.RecommendHistory;
import com.sh.commercechatbot.review.dto.ReviewRecommendResultDto;
import com.sh.commercechatbot.reviewChatbot.dto.request.ResolvedProductDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatSessionDto {

    /**
     * ChatSession의 역할
     * - 현재 대화하고 있는 사용자의 대화정보 저장 + 사용자가 입력한 메세지 저장
     * -
     */
    private ConversationStep step;
    private ResolvedProductDto product; // 사용자가 요청한 상품 추출
    private List<ResolvedProductDto> productCandidates;
    private ReviewRecommendResultDto recommendResult;

    public ChatSessionDto(ConversationStep step) {
        this.step = step;

    }

    public static ChatSessionDto initial() {
        return new ChatSessionDto(
                null,
                null,
                List.of(),
                null
        );
    }
}