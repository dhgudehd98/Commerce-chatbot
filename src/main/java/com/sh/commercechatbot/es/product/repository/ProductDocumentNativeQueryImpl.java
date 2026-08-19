package com.sh.commercechatbot.es.product.repository;

import co.elastic.clients.elasticsearch._types.query_dsl.TextQueryType;
import com.sh.commercechatbot.es.document.product.ProductDocument;
import com.sh.commercechatbot.es.product.dto.ProductSearchResultDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import reactor.core.publisher.Flux;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class ProductDocumentNativeQueryImpl implements ProductDocumentNativeQuery{

    private final ReactiveElasticsearchOperations operations;

    @Override
    public Flux<ProductSearchResultDto> search(String keyword) {
        log.info("[상품 검색 키워드] 키워드 : {}", keyword);

        NativeQuery query = NativeQuery.builder()
                // Query 설정
                .withQuery(q -> q
                                .bool(b -> b
                                                .must(m -> m
                                                        .multiMatch(mm -> mm
                                                                .query(keyword)
                                                                .fields(
                                                                        "variantName^100"
                                                                )
                                                                .type(TextQueryType.CrossFields)
                                                        )
                                                )
                                )
                )
                // 페이징 설정
                .withPageable(PageRequest.of(0, 5))
                .build();

        return operations.search(query, ProductDocument.class)
                .map(hits -> {
                    ProductSearchResultDto searchResultDto = new ProductSearchResultDto(hits.getContent());
                    return searchResultDto;
                });

    }
}