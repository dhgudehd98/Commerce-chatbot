package com.sh.commercechatbot.reviewChatbot.dto.response;


import com.sh.commercechatbot.enums.chatbot.request.ChatIntent;
import com.sh.commercechatbot.enums.chatbot.request.NextAction;
import com.sh.commercechatbot.enums.chatbot.request.OptionalField;
import com.sh.commercechatbot.enums.chatbot.request.RequiredField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ConversationAnalysisResponseDto {
    private ChatIntent intent;
    private ExtractedUserDto criteria;
    private List<RequiredField> missingRequiredFields;
    private List<OptionalField> missingOptionalFields;
    private NextAction nextAction;
}