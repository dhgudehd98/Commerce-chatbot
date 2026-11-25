package com.sh.commercechatbot.enums.chatbot.request;

public enum NextAction {
    ASK_PRODUCT, // 상품명과 상품 ID에 대한 값 없음
    RESOLVE_PRODUCT, // 상품명 확보
    ASK_PRODUCT_SELECTION, // 상품 후보가 여러개
    ASK_OPTIONAL_BODY_INFO, // 상품 확정 && 신체 정보 없음
    SEARCH_REVIEWS, // 상품은 확정되고, 상품에 대한 검색 가능
    GENERATE_RECOMMENDATION,
    COMPLETE,
    OUT_OF_SCOPE // 지원 범위 밖의 질문
}
