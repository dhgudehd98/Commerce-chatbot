package com.sh.commercechatbot.review.dto;

import com.sh.commercechatbot.enums.review.FitPreference;
import com.sh.commercechatbot.enums.review.ReviewSearchScope;
import com.sh.commercechatbot.enums.review.ReviewSearchType;
import com.sh.commercechatbot.es.review.document.ReviewDocument;
import com.sh.commercechatbot.reviewChatbot.dto.response.ConversationAnalysisResponseDto;
import com.sh.commercechatbot.reviewChatbot.dto.response.ExtractedUserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRecommendResultDto {

    private static final int MAX_HISTORY_SIZE = 5;

    // 원하는 상품
    private Long variantId;
    // 채팅한 사용자의 신체정보와 선호핏
    private UserCondition userCondition;

    // ES에서 검색된 리뷰 개수
    private Integer totalReviewCnt;

    private ReviewSearchScope searchScope;

    // ES에서 검색된 리뷰 정보들
    private List<ReviewEvidence> evidences;

    private List<RecommendHistory> contexts;

    public static ReviewRecommendResultDto createReviewResultDto(Long variantId, ConversationAnalysisResponseDto analysisResponseDto,ReviewSearchScope searchScope, List<ReviewEvidence> evidences) {

        UserCondition userCondition = new UserCondition(
                analysisResponseDto.getHeightCm() == null ? null : analysisResponseDto.getHeightCm(),
                analysisResponseDto.getWeightKg() == null ? null : analysisResponseDto.getWeightKg(),
                analysisResponseDto.getFitPreference() == null ? null : analysisResponseDto.getFitPreference()
        );
        return new ReviewRecommendResultDto(
                variantId,
                userCondition,
                evidences.size(),
                searchScope,
                evidences
        );
    }

    public ReviewRecommendResultDto(Long variantId, UserCondition userCondition, Integer totalReviewCnt, ReviewSearchScope searchScope, List<ReviewEvidence> evidences) {
        this.variantId = variantId;
        this.userCondition = userCondition;
        this.totalReviewCnt = totalReviewCnt;
        this.searchScope = searchScope;
        this.evidences = evidences;
    }

    public void addHistory(RecommendHistory recommendHistory) {

        if (contexts == null) {
            contexts = new ArrayList<>();
        }

        contexts.add(recommendHistory);

        if(contexts.size() > MAX_HISTORY_SIZE) contexts.remove(0);
    }


}