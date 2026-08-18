package com.sh.commercechatbot.reviewChatbot.service;

import com.sh.commercechatbot.reviewChatbot.dto.request.ChatRequestDto;
import com.sh.commercechatbot.reviewChatbot.dto.response.ChatResponseDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public interface ReviewChatbotService {

    Flux<ChatResponseDto> chat(ChatRequestDto requestDto, Long memberId);
    Mono<ChatResponseDto> chatNonStream(ChatRequestDto requestDto, Long memberId);
}