package com.sh.commercechatbot.common.exception.outbox.review;

import com.sh.commercechatbot.enums.review.reviewEvent.ReviewEmbeddingFailureCode;
import lombok.Getter;

@Getter
public class ReviewEmbeddingException extends RuntimeException{

    private final ReviewEmbeddingFailureCode failureCode;

    public ReviewEmbeddingException(
            ReviewEmbeddingFailureCode failureCode,
            Throwable cause
    ) {
        super(failureCode.name(), cause);
        this.failureCode = failureCode;
    }
}