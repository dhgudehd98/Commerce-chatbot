package com.sh.commercechatbot.es.product.dto;

import com.sh.commercechatbot.es.product.document.ProductDocument;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductSearchResultDto {
    private Long variantId;
    private String productName;

    public ProductSearchResultDto(ProductDocument document) {
        this.variantId = document.getProductVariantId();
        this.productName = document.getVariantName();
    }
}