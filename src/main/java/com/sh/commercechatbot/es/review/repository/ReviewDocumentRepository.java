package com.sh.commercechatbot.es.review.repository;

import com.sh.commercechatbot.es.review.document.ReviewDocument;
import com.sh.commercechatbot.es.review.dto.ReviewOptionalInfoDto;
import com.sh.commercechatbot.review.dto.ReviewEvidence;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


@Repository
public interface ReviewDocumentRepository extends
        ReactiveElasticsearchRepository<ReviewDocument, Long>,
        ReviewDocumentNativeQuery {

    Mono<List<ReviewDocument>> searchReviewAll(Long variantId);
}