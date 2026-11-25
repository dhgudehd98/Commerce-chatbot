package com.sh.commercechatbot.gpt.dto.response;

import com.sh.commercechatbot.common.exception.chatbot.ChatbotErrorType;
import com.sh.commercechatbot.common.exception.chatbot.ChatbotException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GptResponseDto {
    private List<GptChoice> choices;

    public GptChoice getSingleChoice() {

        return choices.stream().findFirst().orElseThrow(() -> new ChatbotException("[Gpt Response Error] : No GptChoices", ChatbotErrorType.GPT_RESPONSE_ERROR));
    }
}