package com.sh.commercechatbot.enums.payment;

public enum PaymentStatus {
    READY,
    // 결제 승인 요청 확인 Status
    CONFIRMING,
    PAID,
    FAILED,
    CANCELED,
    REFUNDED
}
