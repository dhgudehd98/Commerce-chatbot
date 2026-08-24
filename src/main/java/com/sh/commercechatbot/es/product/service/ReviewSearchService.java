package com.sh.commercechatbot.es.product.service;



import com.sh.commercechatbot.es.review.document.ReviewDocument;
import com.sh.commercechatbot.es.review.dto.ReviewOptionalInfoDto;
import com.sh.commercechatbot.es.review.repository.ReviewDocumentRepository;
import com.sh.commercechatbot.review.dto.ReviewEvidence;
import com.sh.commercechatbot.review.dto.ReviewRecommendResultDto;
import com.sh.commercechatbot.reviewChatbot.dto.request.ChatRequestDto;
import com.sh.commercechatbot.reviewChatbot.dto.response.ConversationAnalysisResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewSearchService {

    private final ReviewDocumentRepository reviewDocumentRepository;

    public Mono<List<ReviewDocument>> searchReviewByPreferenceQuery(Long variantId, ReviewOptionalInfoDto optionalInfoDto) {
        log.info("[상퓸 리뷰 선호 핏 유사도 검색] : ");

        // 사용자가 선호핏에 대한 정보를 입력하지 않은 경우에
        if(optionalInfoDto.getPreferenceQuery() == null || optionalInfoDto.getPreferenceQuery().isBlank())
            return Mono.just(List.of());

        return reviewDocumentRepository.searchByPreferenceQuery(variantId, optionalInfoDto.getPreferenceQuery());
    }

    public Mono<List<ReviewDocument>> searchReviewByBodyInfo(Long variantId, ReviewOptionalInfoDto optionalInfoDto) {
        log.info("[상품 리뷰 사용자 신체정보 기반 검색");

        return reviewDocumentRepository.searchReviewByBodyInfo(variantId, optionalInfoDto);

    }

}