package com.sh.commercechatbot.es.product.repository;

import com.sh.commercechatbot.es.product.dto.ProductSearchResultDto;
import reactor.core.publisher.Flux;

import java.util.List;

public interface ProductDocumentNativeQuery {
    Flux<ProductSearchResultDto> search(String keyword);
}
