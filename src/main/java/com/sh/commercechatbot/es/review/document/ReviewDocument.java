package com.sh.commercechatbot.es.review.document;

import com.sh.commercechatbot.enums.review.FitEvaluation;
import com.sh.commercechatbot.enums.review.FitPreference;
import com.sh.commercechatbot.review.dto.ReviewDocumentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Document(indexName = "review", createIndex = false)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Slf4j
public class ReviewDocument {
    @Id
    private Long reviewId;

    @Field(type = FieldType.Keyword)
    private Long productVariantId;

    @Field(type = FieldType.Keyword)
    private Long productOptionId;

    @Field(type = FieldType.Keyword)
    private String purchasedSize;

    @Field(type = FieldType.Text)
    private String content;

    @Field(type = FieldType.Dense_Vector)
    private float[] contentEmbedding;

    @Field(type = FieldType.Integer)
    private Integer heightCm;

    @Field(type = FieldType.Integer)
    private Integer weightKg;

    @Field(type = FieldType.Keyword)
    private FitEvaluation fitEvaluation;

    @Field(type = FieldType.Keyword)
    private FitPreference fitPreference;

    @Field(type = FieldType.Date)
    private LocalDateTime createdAt;

    @Field(type = FieldType.Date)
    private LocalDateTime sourceUpdatedAt;


}