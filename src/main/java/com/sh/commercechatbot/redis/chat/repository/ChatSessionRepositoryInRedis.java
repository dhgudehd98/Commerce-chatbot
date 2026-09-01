package com.sh.commercechatbot.redis.chat.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sh.commercechatbot.redis.chat.dto.ChatSessionDto;
import com.sh.commercechatbot.review.dto.RecommendHistory;
import com.sh.commercechatbot.review.dto.ReviewRecommendResultDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.Duration;


@RequiredArgsConstructor
@Component
@Slf4j
public class ChatSessionRepositoryInRedis {
    private static final String KEY_PREFIX = "chat:session:";
    private static final Duration STATE_TTL = Duration.ofMinutes(60);
    private final ReactiveStringRedisTemplate redisTemplate;
    private final ObjectMapper mapper;

    private String createKey(Long memberId) {
        log.info("[새로운 대화 생성] memberId : {}", memberId);
        return KEY_PREFIX + memberId;
    }

    public Mono<ChatSessionDto> findChatSession(Long memberId) {
        String key = createKey(memberId);

        return redisTemplate.opsForValue()
                .get(key)
                .map(stateString -> {
                    try {
                        return mapper.readValue(stateString, ChatSessionDto.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    public Mono<Boolean> save(Long memberId, ChatSessionDto chatSessionDto){

        String key = createKey(memberId);
        return Mono.fromCallable(() ->
                        mapper.writeValueAsString(chatSessionDto)
                )
                .flatMap(jsonState ->
                        redisTemplate.opsForValue()
                                .set(key, jsonState, STATE_TTL)
                );
    }

    public Mono<Boolean> delete(Long memberId) {
        String key = createKey(memberId);
        log.info("[사용자 대화 State 삭제] memberId : {}",memberId);

        return redisTemplate.delete(key)
                .map(deletedCount -> deletedCount > 0);
    }
}