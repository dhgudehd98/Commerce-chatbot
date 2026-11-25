package com.sh.commercechatbot.review.dto;

import com.sh.commercechatbot.entity.Review;
import com.sh.commercechatbot.enums.review.FitEvaluation;
import com.sh.commercechatbot.enums.review.ReviewSearchType;
import com.sh.commercechatbot.es.review.document.ReviewDocument;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewEvidence {

    private ReviewSearchType searchType; // 유사도 검색으로 조회한건지, 신체정보 기반으로 검색한건지
    private Long reviewId; // reviewId
    private String purchasedSize; // 구매 사이즈
    private FitEvaluation fitEvaluation; // 사이즈 후기
    private String content; // 리뷰 후기

    public ReviewEvidence(ReviewDocument reviewDocument, ReviewSearchType reviewSearchType) {
        this.searchType = reviewSearchType;
        this.reviewId = reviewDocument.getReviewId();
        this.purchasedSize = reviewDocument.getPurchasedSize();
        this.fitEvaluation = reviewDocument.getFitEvaluation();
        this.content = reviewDocument.getContent();
    }
}