package com.sh.commercechatbot.reviewChatbot.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.sh.commercechatbot.reviewChatbot.dto.request.ChatRequestDto;
import com.sh.commercechatbot.reviewChatbot.dto.response.ChatResponseDto;
import com.sh.commercechatbot.reviewChatbot.service.ReviewChatbotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ReviewChatbotController {


    private final ReviewChatbotService reviewChatbotService;

//    @PostMapping("stream")
//    public Flux<ChatResponseDto> chat(
//            @RequestBody ChatRequestDto request
//    ) throws JsonProcessingException {
//        Long memberId = 1L;
//        return reviewChatbotService.chat(request, memberId);
//    }

    @PostMapping("")
    public Mono<ChatResponseDto> chat(
            @RequestBody ChatRequestDto request
    ) throws JsonProcessingException {
        Long memberId = 1L;
        return reviewChatbotService.chat(memberId, request);
    }

}