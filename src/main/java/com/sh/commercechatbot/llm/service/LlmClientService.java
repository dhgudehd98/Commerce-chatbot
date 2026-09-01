package com.sh.commercechatbot.llm.service;


import com.sh.commercechatbot.common.exception.chatbot.ChatbotError;
import com.sh.commercechatbot.common.exception.chatbot.ChatbotException;
import com.sh.commercechatbot.llm.dto.request.LlmChatRequestDto;
import com.sh.commercechatbot.llm.dto.response.LlmChatResponseDto;
import com.sh.commercechatbot.llm.enums.LlmType;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public interface LlmClientService {
    LlmType getLlmType();
    Flux<LlmChatResponseDto> get(LlmChatRequestDto llmChatRequestDto);

    Mono<LlmChatResponseDto> getNonStream(LlmChatRequestDto llmChatRequestDto);

    default Mono<LlmChatResponseDto> getNonStreamWithException(LlmChatRequestDto llmChatRequestDto) {
        return getNonStream(llmChatRequestDto)
                .onErrorResume(exception ->{
                    if (exception instanceof ChatbotException chatbotException) {
                        ChatbotError chatbotError = new ChatbotError(chatbotException.getChatbotErrorType().getCode(), chatbotException.getMessage());
                        return Mono.just(new LlmChatResponseDto(chatbotError));
                    } else {
                        ChatbotError chatbotError = new ChatbotError(500, exception.getMessage());

                        return Mono.just(new LlmChatResponseDto(chatbotError));
                    }
                });
    }
}