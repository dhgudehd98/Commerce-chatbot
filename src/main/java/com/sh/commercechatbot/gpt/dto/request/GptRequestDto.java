package com.sh.commercechatbot.gpt.dto.request;

import com.sh.commercechatbot.gpt.dto.response.GptResponseFormat;
import com.sh.commercechatbot.llm.enums.LlmModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GptRequestDto {
    private List<GptRequestCompletionDto> messages;
    private LlmModel model;
    private Boolean stream;
    private GptResponseFormat response_format;
}