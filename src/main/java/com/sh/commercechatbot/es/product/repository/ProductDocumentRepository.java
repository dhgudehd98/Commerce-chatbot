package com.sh.commercechatbot.es.product.repository;

import com.sh.commercechatbot.es.product.document.ProductDocument;
import com.sh.commercechatbot.es.review.repository.ReviewDocumentNativeQuery;
import com.sh.commercechatbot.review.es.ReviewDocument;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ProductDocumentRepository extends
        ReactiveElasticsearchRepository<ProductDocument, Long>,
        ReviewDocumentNativeQuery
{
}
