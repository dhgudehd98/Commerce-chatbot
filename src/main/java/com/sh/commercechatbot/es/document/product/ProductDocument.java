package com.sh.commercechatbot.es.document.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.util.ArrayList;
import java.util.List;

@Document(indexName = "products", createIndex = false)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Slf4j
public class ProductDocument {


    @Id
    private Long productVariantId;

    @Field(type = FieldType.Long)
    private Long productId;

    @MultiField(
            mainField = @Field(
                    type = FieldType.Text,
                    analyzer = "products_index_analyzer",
                    searchAnalyzer = "products_search_analyzer"
            ),
            otherFields = {
                    @InnerField(suffix = "keyword", type = FieldType.Keyword)
            }
    )
    private String baseProductName;

    @MultiField(
            mainField = @Field(
                    type = FieldType.Text,
                    analyzer = "products_index_analyzer",
                    searchAnalyzer = "products_search_analyzer"
            ),
            otherFields = {
                    @InnerField(suffix = "keyword", type = FieldType.Keyword)
            }
    )
    private List<String> tags = new ArrayList<>();

    @MultiField(
            mainField = @Field(
                    type = FieldType.Text,
                    analyzer = "products_index_analyzer",
                    searchAnalyzer = "products_search_analyzer"
            ),
            otherFields = {
                    @InnerField(suffix = "keyword", type = FieldType.Keyword)
            }
    )
    private String variantName;

    @Field(
            type = FieldType.Text,
            analyzer = "products_index_analyzer",
            searchAnalyzer = "products_search_analyzer"
    )
    private String productDescription;

    @Field(type = FieldType.Long)
    private Long brandId;

    @MultiField(
            mainField = @Field(
                    type = FieldType.Text,
                    analyzer = "products_index_analyzer",
                    searchAnalyzer = "products_search_analyzer"
            ),
            otherFields = {
                    @InnerField(suffix = "keyword", type = FieldType.Keyword)
            }
    )
    private String brandName;

    @Field(type = FieldType.Keyword)
    private String category;

    @Field(type = FieldType.Keyword)
    private String color;

    @Field(type = FieldType.Keyword)
    private String modelNumber;

    @Field(type = FieldType.Integer)
    private Integer price;

    @Field(type = FieldType.Keyword)
    private String productVariantStatus;

    @Field(type = FieldType.Keyword, index = false)
    private String thumbnailUrl;

    @Field(type = FieldType.Keyword, index = false)
    private List<String> imageUrls;

    @Field(type = FieldType.Nested)
    private List<ProductOptionDocument> options;

    @Field(type = FieldType.Integer)
    private Integer totalAvailableStock;

    @Field(type = FieldType.Boolean)
    private Boolean inStock;

    @Field(type = FieldType.Dense_Vector, dims = 1536)
    private float[] descriptionVector;

}