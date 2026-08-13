package com.sh.commercechatbot.common.exception.outbox;

public class OutboxException extends RuntimeException{

    public OutboxException(String message) {
        super(message);
    }
}