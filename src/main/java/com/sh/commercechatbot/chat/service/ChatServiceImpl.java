package com.sh.commercechatbot.chat.service;

import com.sh.commercechatbot.chat.dto.request.ChatRequestDto;
import com.sh.commercechatbot.chat.dto.response.ChatResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatServiceImpl implements ChatService{
    @Override
    public Flux<ChatResponseDto> chat(ChatRequestDto requestDto) {
        return null;
    }
}