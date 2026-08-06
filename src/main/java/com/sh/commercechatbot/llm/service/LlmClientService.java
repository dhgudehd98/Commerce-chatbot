package com.sh.commercechatbot.llm.service;


import com.sh.commercechatbot.llm.dto.request.LlmChatRequestDto;
import com.sh.commercechatbot.llm.dto.response.LlmChatResponseDto;
import com.sh.commercechatbot.llm.enums.LlmType;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public interface LlmClientService {
    LlmType getLlmType();
    Flux<LlmChatResponseDto> get(LlmChatRequestDto llmChatRequestDto);
}