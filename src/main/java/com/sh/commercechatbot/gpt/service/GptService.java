package com.sh.commercechatbot.gpt.service;

import com.sh.commercechatbot.common.exception.chatbot.ChatbotError;
import com.sh.commercechatbot.common.exception.chatbot.ChatbotErrorType;
import com.sh.commercechatbot.common.exception.chatbot.ChatbotException;
import com.sh.commercechatbot.gpt.dto.request.GptRequestDto;
import com.sh.commercechatbot.gpt.dto.response.GptResponseDto;
import com.sh.commercechatbot.llm.dto.request.LlmChatRequestDto;
import com.sh.commercechatbot.llm.dto.response.LlmChatResponseDto;
import com.sh.commercechatbot.llm.enums.LlmType;
import com.sh.commercechatbot.llm.service.LlmClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class GptService implements LlmClientService {

    @Value("${spring.ai.openai.api-key}")
    private String gptApiKey;

    private final WebClient webClient;

    @Override
    public LlmType getLlmType() {
        return LlmType.GPT;
    }

    // 여기서 실제로 Gpt한테 요청
    @Override
    public Flux<LlmChatResponseDto> get(LlmChatRequestDto llmChatRequestDto) {
        GptRequestDto gptRequestDto = new GptRequestDto(llmChatRequestDto, true);
        return webClient.post()
                .uri("https://api.openai.com/v1/chat/completions")
                .header("Authorization", "Bearer " + gptApiKey)
                .bodyValue(gptRequestDto)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(body ->{
                                log.error("Error Response : {}", body);
                                return Mono.error(new ChatbotException("LLM GPT API 요청 실패 : " + body, ChatbotErrorType.GPT_RESPONSE_ERROR));
                            });
                }))
                .bodyToFlux(GptResponseDto.class)
                .takeWhile(response -> Optional.ofNullable(response.getSingleChoice().getFinish_reason()).isEmpty())
                .map(gptResponseDto -> {
                    try {
                        log.info("GPT 응답 값 : {}", gptResponseDto.getSingleChoice().getMessage().getContent());
                        return LlmChatResponseDto.getLlmChatResponseDtoFromNotStream(gptResponseDto);
                    } catch (Exception e) {
                        log.error("[GPT Response Error] : " + e.getMessage());
                        return new LlmChatResponseDto(new ChatbotError("500" , e.getMessage()));
                    }
                });
    }

    @Override
    public Mono<LlmChatResponseDto> getNonStream(LlmChatRequestDto llmChatRequestDto) {
        GptRequestDto request = new GptRequestDto(llmChatRequestDto, false);
        return webClient.post()
                .uri("https://api.openai.com/v1/chat/completions")
                .header("Authorization", "Bearer " + gptApiKey)
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(body ->{
                                log.error("Error Response : {}", body);
                                return Mono.error(new ChatbotException("LLM GPT API 요청 실패 : " + body, ChatbotErrorType.GPT_RESPONSE_ERROR));
                            });
                }))
                .bodyToMono(GptResponseDto.class)
                .map(response -> {
                    String content = response
                            .getSingleChoice()
                            .getMessage()
                            .getContent();

                    return new LlmChatResponseDto(content);
                });
    }
}