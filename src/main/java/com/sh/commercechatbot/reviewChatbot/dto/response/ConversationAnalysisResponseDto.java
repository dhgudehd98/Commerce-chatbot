package com.sh.commercechatbot.reviewChatbot.dto.response;


import com.sh.commercechatbot.enums.chatbot.request.ChatIntent;
import com.sh.commercechatbot.enums.chatbot.request.NextAction;
import com.sh.commercechatbot.enums.chatbot.request.OptionalField;
import com.sh.commercechatbot.enums.chatbot.request.RequiredField;
import com.sh.commercechatbot.enums.review.FitPreference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ConversationAnalysisResponseDto {
    private ChatIntent intent;
    private Long variantId;
    private String productName;
    private Integer heightCm;
    private Integer weightKg;
    private FitPreference fitPreference;
    private String semanticQuery;
    private Boolean skipBodyCondition;
}