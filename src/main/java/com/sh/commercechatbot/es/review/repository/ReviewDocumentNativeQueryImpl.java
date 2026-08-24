package com.sh.commercechatbot.es.review.repository;

import co.elastic.clients.elasticsearch._types.KnnQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.TextQueryType;
import co.elastic.clients.json.JsonData;
import com.sh.commercechatbot.es.product.document.ProductDocument;
import com.sh.commercechatbot.es.review.document.ReviewDocument;
import com.sh.commercechatbot.es.review.dto.ReviewOptionalInfoDto;
import com.sh.commercechatbot.es.review.repository.ReviewDocumentNativeQuery;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Slf4j
public class ReviewDocumentNativeQueryImpl implements ReviewDocumentNativeQuery {

    private final ReactiveElasticsearchOperations operations;
    private final EmbeddingModel embeddingModel;
    @Override
    public Mono<List<ReviewDocument>> searchByPreferenceQuery(Long variantId, String preferenceQuery) {


        return Mono.fromCallable(() ->
                        embeddingModel.embed(preferenceQuery)
                )
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(preferenceVectors -> {
                    if (preferenceVectors == null ||
                            preferenceVectors.length == 0) {
                        return Mono.just(List.of());
                    }

                    List<Float> vectors = new ArrayList<>(
                            preferenceVectors.length
                    );

                    for (float vector : preferenceVectors) {
                        vectors.add(vector);
                    }

                    KnnQuery knnQuery = KnnQuery.of(k -> k
                            .field("contentEmbedding")
                            .queryVector(vectors)
                            .k(10)
                            .numCandidates(100)
                            .filter(f -> f
                                    .term(t -> t
                                            .field("variantId")
                                            .value(variantId)
                                    )
                            )
                    );

                    NativeQuery query = NativeQuery.builder()
                            .withKnnQuery(knnQuery)
                            .build();

                    return operations.search(
                                    query,
                                    ReviewDocument.class
                            )
                            .map(SearchHit::getContent)
                            .collectList();
                });

    }

    @Override
    public Mono<List<ReviewDocument>> searchReviewByBodyInfo(Long variantId, ReviewOptionalInfoDto optionalInfoDto) {
        log.info("[사용자 입력 신체정보 값에 따른 상품 리뷰 검색]");
        if (variantId == null || optionalInfoDto == null) {
            return Mono.just(List.of());
        }

        Integer height = optionalInfoDto.getHeight();
        Integer weight = optionalInfoDto.getWeight();

        if (height == null && weight == null) {
            return Mono.just(List.of());
        }

        NativeQuery query = NativeQuery.builder()
                .withQuery(queryBuilder -> queryBuilder
                        .bool(bool -> {
                            bool.filter(filter -> filter
                                    .term(term -> term
                                            .field("productVariantId")
                                            .value(variantId)
                                    )
                            );

                            if (height != null) {
                                bool.filter(filter -> filter
                                        .range(range -> range
                                                .field("heightCm")
                                                .gte(JsonData.of(height -
                                                        5))
                                                .lte(JsonData.of(height +
                                                        5))
                                        )
                                );
                            }

                            if (weight != null) {
                                bool.filter(filter -> filter
                                        .range(range -> range
                                                .field("weightKg")
                                                .gte(JsonData.of(weight -
                                                        5))
                                                .lte(JsonData.of(weight +
                                                        5))
                                        )
                                );
                            }

                            return bool;
                        })
                )
                .withPageable(PageRequest.of(0, 10))
                .build();

        return operations.search(query, ReviewDocument.class)
                .map(SearchHit::getContent)
                .collectList();
    }

}