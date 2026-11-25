package com.sh.commercechatbot.enums.review;

public enum ConversationStep {
    AWAITING_PRODUCT_NAME,
    AWAITING_PRODUCT_SELECTION,
    AWAITING_BODY_INFO,
    SKIP_BODY_INFO,
    REVIEW_ADDITIONAL_REQUEST // 상품 리뷰가 완료 된 후, 추가적으로 질문을 할 수도 있는 경우에
}
