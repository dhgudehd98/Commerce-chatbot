package com.sh.commercechatbot.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RecommendHistory {
    private String userMessage;
    private String llmMessage;
}