package com.sh.commercechatbot.chat.service;

import com.sh.commercechatbot.chat.dto.request.ChatRequestDto;
import com.sh.commercechatbot.chat.dto.response.ChatResponseDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public interface ChatService {

    Flux<ChatResponseDto> chat(ChatRequestDto requestDto);
}