package com.sh.commercechatbot.redis.chat;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sh.commercechatbot.reviewChatbot.dto.request.ChatConversationState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


import java.time.Duration;

@Component
@Slf4j
@RequiredArgsConstructor
public class ChatConversationRepositoryInRedis {
    private static final String KEY_PREFIX = "chatbot:conversation:";
    private static final Duration STATE_TTL = Duration.ofMinutes(60);
    private final ReactiveStringRedisTemplate redisTemplate;
    private final ObjectMapper mapper;

    private String createKey(Long memberId) {
        log.info("[새로운 대화 생성] memberId : {}", memberId);
        return KEY_PREFIX + memberId;
    }

    public Mono<ChatConversationState> findStateByMemberId(Long memberId) {
        String key = createKey(memberId);

        return redisTemplate.opsForValue()
                .get(key)
                .map(stateString -> {
                    try {
                        return mapper.readValue(stateString, ChatConversationState.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    public Mono<Boolean> save(Long memberId, ChatConversationState state) throws JsonProcessingException {
        String key = createKey(memberId);
        String jsonState = mapper.writeValueAsString(state);

        log.info("[사용자 대화 State 저장] memberId : {}, state : {}", memberId, state);

        return redisTemplate.opsForValue()
                .set(key, jsonState, STATE_TTL);
    }

    public Mono<Boolean> delete(Long memberId) {
        String key = createKey(memberId);
        log.info("[사용자 대화 State 삭제] memberId : {}",memberId);

        return redisTemplate.delete(key)
                .map(deletedCount -> deletedCount > 0);
    }

}