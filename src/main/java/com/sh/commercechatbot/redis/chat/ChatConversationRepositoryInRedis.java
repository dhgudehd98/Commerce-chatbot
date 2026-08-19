package com.sh.commercechatbot.redis.chat;


import com.sh.commercechatbot.reviewChatbot.dto.request.ChatConversationState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import tools.jackson.databind.ObjectMapper;

import java.time.Duration;

@Component
@Slf4j
@RequiredArgsConstructor
public class ChatConversationRepositoryInRedis {
    private static final String KEY_PREFIX = "chatbot:conversation:";
    private static final Duration STATE_TTL = Duration.ofMinutes(10);
    private final ReactiveStringRedisTemplate redisTemplate;
    private final ObjectMapper mapper;

    private String createKey(Long memberId) {
        return KEY_PREFIX + memberId;
    }

    public Mono<ChatConversationState> findStateByMemberId(Long memberId) {
        String key = createKey(memberId);

        return redisTemplate.opsForValue()
                .get(key)
                .map(stateString -> mapper.readValue(stateString, ChatConversationState.class));
    }

    public Mono<Boolean> save(Long memberId, ChatConversationState state) {
        String key = createKey(memberId);
        String jsonState = mapper.writeValueAsString(state);

        return redisTemplate.opsForValue()
                .set(key, jsonState, STATE_TTL);
    }

    public Mono<Boolean> delete(Long memberId) {
        String key = createKey(memberId);

        return redisTemplate.delete(key)
                .map(deletedCount -> deletedCount > 0);
    }

}