package com.sh.commercechatbot.reviewChatbot.service;

import com.sh.commercechatbot.reviewChatbot.dto.request.ChatRequestDto;
import com.sh.commercechatbot.reviewChatbot.dto.response.ChatResponseDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public interface ReviewChatbotService {

    Flux<ChatResponseDto> chat(ChatRequestDto requestDto);
}