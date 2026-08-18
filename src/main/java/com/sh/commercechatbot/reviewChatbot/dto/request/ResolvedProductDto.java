package com.sh.commercechatbot.reviewChatbot.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResolvedProductDto {
    private Long variantId;
    private String productName;
}