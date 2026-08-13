package com.sh.commercechatbot.enums.review.reviewEvent;

public enum OutboxPublishStatus {
    PENDING,
    PUBLISHING,
    PUBLISHED,
    RETRY_WAIT,
    FAILED
}