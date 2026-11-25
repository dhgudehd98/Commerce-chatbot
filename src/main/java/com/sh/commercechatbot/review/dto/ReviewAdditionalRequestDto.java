package com.sh.commercechatbot.review.dto;

import com.sh.commercechatbot.redis.chat.dto.ChatSessionDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewAdditionalRequestDto {
    private ChatSessionDto session;
    private String currentMessage;
}