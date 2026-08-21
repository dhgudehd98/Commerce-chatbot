package com.sh.commercechatbot.es.review.repository;

import com.sh.commercechatbot.es.review.document.ReviewDocument;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReviewDocumentRepository extends
        ReactiveElasticsearchRepository<ReviewDocument, Long>,
        ReviewDocumentNativeQuery {

}