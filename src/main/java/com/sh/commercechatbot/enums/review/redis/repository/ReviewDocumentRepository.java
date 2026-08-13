package com.sh.commercechatbot.enums.review.redis.repository;

import com.sh.aicommerce.enums.review.es.ReviewDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReviewDocumentRepository extends
        ElasticsearchRepository<ReviewDocument, Long> {
}