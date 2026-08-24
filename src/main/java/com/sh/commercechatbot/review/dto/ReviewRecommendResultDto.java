package com.sh.commercechatbot.review.dto;

import com.sh.commercechatbot.enums.review.FitPreference;
import com.sh.commercechatbot.es.review.document.ReviewDocument;
import com.sh.commercechatbot.reviewChatbot.dto.response.ExtractedUserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRecommendResultDto {

    // 원하는 상품
    private Long variantId;
    // 채팅한 사용자의 신체정보와 선호핏
    private UserCondition userCondition;

    // ES에서 검색된 리뷰 개수
    private Integer totalReviewCnt;
    // ES에서 검색된 리뷰 정보들
    private List<ReviewEvidence> evidences;
}