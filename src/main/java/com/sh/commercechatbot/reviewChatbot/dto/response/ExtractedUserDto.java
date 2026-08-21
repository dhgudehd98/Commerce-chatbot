package com.sh.commercechatbot.reviewChatbot.dto.response;

import com.sh.commercechatbot.enums.review.FitPreference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ExtractedUserDto {
    private Long variantId;
    private String productName;
    private Integer heightCm;
    private Integer weightKg;
    private FitPreference fitPreference;
    private String semanticQuery;
    private Boolean skipBodyCondition;
}