package com.sh.commercechatbot.es.product.repository;

import com.sh.commercechatbot.es.document.product.ProductDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDocumentRepository extends
        ReactiveElasticsearchRepository<ProductDocument, Long>,
        ProductDocumentNativeQuery
{

}
