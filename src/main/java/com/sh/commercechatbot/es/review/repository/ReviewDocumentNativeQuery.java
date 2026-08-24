package com.sh.commercechatbot.es.review.repository;


import com.sh.commercechatbot.es.review.document.ReviewDocument;
import com.sh.commercechatbot.es.review.dto.ReviewOptionalInfoDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ReviewDocumentNativeQuery {
    Mono<List<ReviewDocument>> searchByPreferenceQuery(Long variantId, String preferenceQuery);
    Mono<List<ReviewDocument>> searchReviewByBodyInfo(Long variantId, ReviewOptionalInfoDto optionalInfoDto);

}
