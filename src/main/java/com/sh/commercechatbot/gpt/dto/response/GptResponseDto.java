package com.sh.commercechatbot.gpt.dto.response;

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
        return choices.stream().findFirst().orElseThrow();
    }
}