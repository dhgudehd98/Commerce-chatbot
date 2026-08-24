package com.sh.commercechatbot.review.service;

import com.sh.commercechatbot.enums.review.FitPreference;
import com.sh.commercechatbot.es.review.document.ReviewDocument;
import com.sh.commercechatbot.review.dto.ReviewEvidence;
import com.sh.commercechatbot.review.dto.ReviewRecommendResultDto;
import com.sh.commercechatbot.review.dto.UserCondition;
import com.sh.commercechatbot.reviewChatbot.dto.request.ChatRequestDto;
import com.sh.commercechatbot.reviewChatbot.dto.response.ConversationAnalysisResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewRecommendService {

    public ReviewRecommendResultDto createReviewRequest(Long variantId, ConversationAnalysisResponseDto analysisResponseDto, List<ReviewEvidence> evidences) {

        Integer height = analysisResponseDto.getCriteria().getHeightCm() == null ? null : analysisResponseDto.getCriteria().getHeightCm();
        Integer weight = analysisResponseDto.getCriteria().getWeightKg() == null ? null : analysisResponseDto.getCriteria().getWeightKg();
        FitPreference preference = analysisResponseDto.getCriteria().getFitPreference() == null ? null : analysisResponseDto.getCriteria().getFitPreference();
        UserCondition userCondition = new UserCondition(height, weight, preference);

        return new ReviewRecommendResultDto(variantId, userCondition, evidences.size(), evidences);
    }
}