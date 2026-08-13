package com.sh.commercechatbot.entity;


import com.sh.commercechatbot.common.exception.payment.PaymentException;
import com.sh.commercechatbot.enums.payment.CardCompany;
import com.sh.commercechatbot.enums.payment.PaymentMethod;
import com.sh.commercechatbot.enums.payment.PaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    private Orders order;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod;

    private String provider;

    private String cardCode;
    @Enumerated(EnumType.STRING)
    private CardCompany cardCompany;

    private Integer installmentMonths; // 0 || 1 -> 일시불 , 그 외 할부

    private Long savedCardId;
    private Long savedAccountId;

    @Column(nullable = false)
    private String paymentProvider;

    @Column(nullable = false)
    private String paymentMethodLabel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    @Column(nullable = false)
    private Integer amount;

    @Column(unique = true)
    private String paymentKey; // 결제사(PG)가 발급하는 결제 건의 식별자

    private String approvedNumber; // 승인번호

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime paidAt;



    public void setStatusConfirmingInWidget(String paymentKey) {
        if (this.status != PaymentStatus.READY) {
            throw new PaymentException("결제 승인 가능한 상태가 아닙니다.");
        }
        this.paymentKey = paymentKey;
        this.updatedAt = LocalDateTime.now();
        this.status = PaymentStatus.CONFIRMING;
    }

    public void setStatusConfirmingInBillingCard() {
        if (this.status != PaymentStatus.READY) {
            throw new PaymentException("결제 승인 가능한 상태가 아닙니다.");
        }
        this.status = PaymentStatus.CONFIRMING;
        this.updatedAt = LocalDateTime.now();
    }

    public void setOrder(Orders orders) {
        this.order = orders;
    }

    public void updateFailPaymentTossWidget() {
        this.status = PaymentStatus.FAILED;
        this.updatedAt = LocalDateTime.now();
    }
}
