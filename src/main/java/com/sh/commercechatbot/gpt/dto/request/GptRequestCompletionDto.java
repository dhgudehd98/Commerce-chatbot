package com.sh.commercechatbot.gpt.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GptRequestCompletionDto {

    private GptMessageRole role;
    private String content;
}