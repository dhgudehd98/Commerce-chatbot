package com.sh.commercechatbot.es.review.repository;

import com.sh.commercechatbot.es.review.document.ReviewDocument;
import com.sh.commercechatbot.es.review.dto.ReviewOptionalInfoDto;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;


@Repository
public interface ReviewDocumentRepository extends
        ReactiveElasticsearchRepository<ReviewDocument, Long>,
        ReviewDocumentNativeQuery {

}