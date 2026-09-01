package com.sh.commercechatbot.common.exception;



import com.sh.commercechatbot.common.exception.auth.AuthException;
import com.sh.commercechatbot.common.exception.brand.BrandException;
import com.sh.commercechatbot.common.exception.card.CardException;
import com.sh.commercechatbot.common.exception.chatbot.ChatbotError;
import com.sh.commercechatbot.common.exception.chatbot.ChatbotErrorResponse;
import com.sh.commercechatbot.common.exception.chatbot.ChatbotErrorType;
import com.sh.commercechatbot.common.exception.chatbot.ChatbotException;
import com.sh.commercechatbot.common.exception.delivery.DeliveryException;
import com.sh.commercechatbot.common.exception.member.MemberException;
import com.sh.commercechatbot.common.exception.order.OrderException;
import com.sh.commercechatbot.common.exception.orderItem.OrderItemException;
import com.sh.commercechatbot.common.exception.outbox.OutboxException;
import com.sh.commercechatbot.common.exception.payment.PaymentException;
import com.sh.commercechatbot.common.exception.product.ProductException;
import com.sh.commercechatbot.common.exception.review.ReviewException;
import com.sh.commercechatbot.common.exception.search.SearchException;
import com.sh.commercechatbot.common.exception.search.WeatherException;
import com.sh.commercechatbot.common.exception.wms.InventoryException;
import com.sh.commercechatbot.common.exception.wms.WarehouseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.security.auth.login.AccountException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<ChatbotErrorResponse> handleGeneralException(Exception e, ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        log.error("[GeneralException] Request URI : {}, Method : {}, Error : {}", request.getURI(), request.getMethod(), e.getMessage(), e);
        ChatbotError chatbotError = new ChatbotError("500", e.getMessage());
        return Mono.just(new ChatbotErrorResponse(chatbotError));
    }

    @ExceptionHandler(ChatbotException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<ChatbotErrorResponse> handleChatbotErrorTypeException(ChatbotException e, ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        log.error("[GeneralException] Request URI : {}, Method : {}, Error : {}", request.getURI(), request.getMethod(), e.getMessage(), e);
        ChatbotError chatbotError = new ChatbotError(e.getChatbotErrorType().getCode(), e.getMessage());
        return Mono.just(new ChatbotErrorResponse(chatbotError));
    }

    @ExceptionHandler(OutboxException.class)
    public ResponseEntity<?> handleOutboxException(OutboxException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("result", "N", "message", e.getMessage()));
    }
    @ExceptionHandler(ReviewException.class)
    public ResponseEntity<?> handleReviewException(ReviewException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("result", "N", "message", e.getMessage()));
    }
    @ExceptionHandler(OrderItemException.class)
    public ResponseEntity<?> handleOrderItemException(OrderItemException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("result", "N", "message", e.getMessage()));
    }
    @ExceptionHandler(DeliveryException.class)
    public ResponseEntity<?> handleDeliveryException(DeliveryException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("result", "N", "message", e.getMessage()));
    }
    @ExceptionHandler(OrderException.class)
    public ResponseEntity<?> handleOrderException(OrderException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("result", "N", "message", e.getMessage()));
    }
    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<?> handlePaymentException(PaymentException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("result", "N", "message", e.getMessage()));
    }
    @ExceptionHandler(AccountException.class)
    public ResponseEntity<?> handleAccountException(AccountException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("result", "N", "message", e.getMessage()));
    }
    @ExceptionHandler(CardException.class)
    public ResponseEntity<?> handleCardException(CardException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("result", "N", "message", e.getMessage()));
    }
    @ExceptionHandler(WeatherException.class)
    public ResponseEntity<?> handleWeatherException(WeatherException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("result", "N", "message", e.getMessage()));
    }

    @ExceptionHandler(SearchException.class)
    public ResponseEntity<?> handleSearchException(SearchException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("result", "N", "message", e.getMessage()));
    }

    @ExceptionHandler(InventoryException.class)
    public ResponseEntity<?> handleInventoryException(InventoryException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("result", "N", "message", e.getMessage()));
    }
    @ExceptionHandler(WarehouseException.class)
    public ResponseEntity<?> handleWarehouseException(WarehouseException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("result", "N", "message", e.getMessage()));
    }
    @ExceptionHandler(ProductException.class)
    public ResponseEntity<?> handleProductException(ProductException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("result", "N", "message", e.getMessage()));
    }
    @ExceptionHandler(BrandException.class)
    public ResponseEntity<?> handleBrandException(BrandException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("result", "N", "message", e.getMessage()));
    }
    @ExceptionHandler(AuthException.class)
    public ResponseEntity<?> handleAuthException(AuthException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("result", "N", "message", e.getMessage()));
    }

    @ExceptionHandler(MemberException.class)
    public ResponseEntity<Map<String, String>> handleMember(MemberException e) {
        Map<String, String> res = new HashMap<>();
        res.put("result", "N");
        res.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
    }

}