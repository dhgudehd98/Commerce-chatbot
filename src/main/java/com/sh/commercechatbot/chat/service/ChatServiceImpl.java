package com.sh.commercechatbot.chat.service;

import com.sh.commercechatbot.chat.dto.request.ChatRequestDto;
import com.sh.commercechatbot.chat.dto.response.ChatResponseDto;
import com.sh.commercechatbot.llm.dto.request.LlmChatRequestDto;
import com.sh.commercechatbot.llm.enums.LlmModel;
import com.sh.commercechatbot.llm.enums.LlmType;
import com.sh.commercechatbot.llm.service.LlmClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatServiceImpl implements ChatService{

    private final Map<LlmType, LlmClientService> llmClientServiceMap;
    @Override
    public Flux<ChatResponseDto> chat(ChatRequestDto requestDto) {
        LlmModel model = requestDto.getLlmModel();

        LlmChatRequestDto llmChatRequestDto = new LlmChatRequestDto(requestDto, "요청에 적절히 응답");
        LlmClientService clientService = llmClientServiceMap.get(model.getLlmType());

        log.info("[사용자 요청 정보] 요청 메세지 :{} 프롬프트 : {}", llmChatRequestDto.getRequest(), llmChatRequestDto.getPrompt());

        return clientService.get(llmChatRequestDto)
                .map(ChatResponseDto::new);
    }

}