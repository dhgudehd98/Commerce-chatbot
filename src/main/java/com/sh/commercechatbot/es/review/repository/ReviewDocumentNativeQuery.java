package com.sh.commercechatbot.es.review.repository;

import com.sh.commercechatbot.review.es.ReviewDocument;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReviewDocumentNativeQuery {
    Mono<ReviewDocument> searchByPreferenceQuery(String preferenceQuery);
}
