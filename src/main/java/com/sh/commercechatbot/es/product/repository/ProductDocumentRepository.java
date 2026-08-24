package com.sh.commercechatbot.es.product.repository;

import com.sh.commercechatbot.es.product.document.ProductDocument;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDocumentRepository extends
        ReactiveElasticsearchRepository<ProductDocument, Long>,
        ProductDocumentNativeQuery
{

}
