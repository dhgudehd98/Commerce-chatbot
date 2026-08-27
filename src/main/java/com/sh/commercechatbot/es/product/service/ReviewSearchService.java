package com.sh.commercechatbot.es.product.service;



import com.sh.commercechatbot.enums.review.ReviewSearchType;
import com.sh.commercechatbot.es.review.document.ReviewDocument;
import com.sh.commercechatbot.es.review.dto.ReviewOptionalInfoDto;
import com.sh.commercechatbot.es.review.repository.ReviewDocumentRepository;
import com.sh.commercechatbot.redis.chat.ChatConversationRepositoryInRedis;
import com.sh.commercechatbot.review.dto.ReviewEvidence;
import com.sh.commercechatbot.review.dto.ReviewRecommendResultDto;
import com.sh.commercechatbot.reviewChatbot.dto.request.ChatConversationState;
import com.sh.commercechatbot.reviewChatbot.dto.request.ChatRequestDto;
import com.sh.commercechatbot.reviewChatbot.dto.request.ResolvedProductDto;
import com.sh.commercechatbot.reviewChatbot.dto.response.ChatResponseDto;
import com.sh.commercechatbot.reviewChatbot.dto.response.ConversationAnalysisResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewSearchService {

    private final ReviewDocumentRepository reviewDocumentRepository;
    private final ChatConversationRepositoryInRedis conversationRepository;

    public Mono<List<ReviewEvidence>> searchReviewAll(Long memberId, ChatRequestDto chatRequestDto, ChatConversationState conversationState, ConversationAnalysisResponseDto analysisResponseDto) {

        ResolvedProductDto resolvedProductDto = conversationState.getProduct();

        if (resolvedProductDto.getVariantId() == null) {
            return Mono.error(new IllegalArgumentException("확정된 상품이 존재하지 않습니다."));
        }


        return reviewDocumentRepository.searchReviewAll(resolvedProductDto.getVariantId())
                .map(reviewDocuments ->
                        reviewDocuments.stream()
                                .map(reviewDocument -> new ReviewEvidence(reviewDocument, ReviewSearchType.SEARCH_ALL))
                                .toList()
                );
    }
    public Mono<List<ReviewEvidence>> searchReview(Long memberId, ChatRequestDto chatRequestDto, ChatConversationState conversationState, ConversationAnalysisResponseDto analysisResponseDto) {

        ReviewOptionalInfoDto optionalInfoDto = new ReviewOptionalInfoDto(
                analysisResponseDto.getHeightCm(),
                analysisResponseDto.getWeightKg(),
                analysisResponseDto.getFitPreference(),
                analysisResponseDto.getSemanticQuery()
        );

        ResolvedProductDto resolvedProductDto = conversationState.getProduct();

        if (resolvedProductDto.getVariantId() == null || resolvedProductDto.getProductName() == null) {
            return Mono.error(new IllegalArgumentException("확정된 상품이 존재하지 않습니다."));
        }

        Long variantId = resolvedProductDto.getVariantId();

        Mono<List<ReviewDocument>> semanticReviews = searchReviewByPreferenceQuery(variantId, optionalInfoDto);
        Mono<List<ReviewDocument>> bodyInfoReviews = searchReviewByBodyInfo(variantId, optionalInfoDto);

        return Mono.zip(semanticReviews, bodyInfoReviews)
                .map(tuple -> mergeReviews(tuple.getT1(), tuple.getT2()));

    }

    private List<ReviewEvidence> mergeReviews(List<ReviewDocument> semanticReviews, List<ReviewDocument> bodyInfoReviews) {

        log.info("[상품 리뷰 합산하기]");
        Map<Long, ReviewEvidence> reviews = new HashMap<>();

        semanticReviews.stream()
                .map(reviewDocument -> new ReviewEvidence(reviewDocument, ReviewSearchType.SEMANTIC_REVIEW))
                .forEach(reviewEvidence -> reviews.put(reviewEvidence.getReviewId(), reviewEvidence));

        bodyInfoReviews.stream()
                .map(reviewDocument -> new ReviewEvidence(reviewDocument, ReviewSearchType.BODY_INFO_REVIEW))
                .forEach(reviewEvidence -> reviews.put(reviewEvidence.getReviewId(), reviewEvidence));

        return reviews.values()
                .stream()
                .limit(70)
                .toList();
    }

    public Mono<List<ReviewDocument>> searchReviewByPreferenceQuery(Long variantId, ReviewOptionalInfoDto optionalInfoDto) {
        log.info("[상퓸 리뷰 선호 핏 유사도 검색] : ");

        // 사용자가 선호핏에 대한 정보를 입력하지 않은 경우에 빈 값 출력
        if(optionalInfoDto.getPreferenceQuery() == null || optionalInfoDto.getPreferenceQuery().isBlank())
            return Mono.just(List.of());

        return reviewDocumentRepository.searchByPreferenceQuery(variantId, optionalInfoDto.getPreferenceQuery());
    }

    public Mono<List<ReviewDocument>> searchReviewByBodyInfo(Long variantId, ReviewOptionalInfoDto optionalInfoDto) {
        log.info("[상품 리뷰 사용자 신체정보 기반 검색");

        return reviewDocumentRepository.searchReviewByBodyInfo(variantId, optionalInfoDto);

    }


}