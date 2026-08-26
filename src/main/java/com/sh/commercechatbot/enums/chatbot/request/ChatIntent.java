package com.sh.commercechatbot.enums.chatbot.request;

public enum ChatIntent {

    /**
     * ChatIntent에 대한 역할은 전체 대화의 고정된 목적이 아닌, 사용자가 입력한 요청이 어떤 의도를 나타내는지 파악하기 위해서 설정
     */
    REQUEST_SIZE_RECOMMENDATION, // "나 상품 사이즈 추천좀 해줘" 와 같은 특정 멘트 없이 상품 추천해달라고 했을 때
    PROVIDE_PRODUCT_NAME, // 사용자가 상품명 입력
    PROVIDE_BODY_INFO, // 사용자가 사용자 정보 입력(키 , 몸무게, 선호핏)
    SKIP_BODY_INFO, // 사용자 정보 입력 생략
    OUT_OF_SCOPE, // 상품 사이즈 추천과 관련 없는 데이터 입력시
    AFTER_REVIEW_RECOMMENDATION_ADDTIONAL_INFO // 상품 리뷰를 완료한 후에 추가 질문이 있는 경우

}
