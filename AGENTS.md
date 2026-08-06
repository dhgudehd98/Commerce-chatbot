# AGENTS-1.md

# 1. 프로젝트 개요

상품 및 구매 리뷰를 Elasticsearch에서 검색하고, 사용자 질문을 분석하여 사이즈 추천
근거를 생성한 뒤 LLM 답변을 SSE로 스트리밍하는 WebFlux Chatbot 서비스다.

Commerce API와 독립된 애플리케이션으로 실행한다.

## 핵심 목표

- Spring WebFlux + Netty 기반 비동기 챗봇 제공
- Elasticsearch 기반 상품 및 리뷰 RAG 검색
- 실제 리뷰 통계 기반 사이즈 추천
- LLM 토큰 SSE 스트리밍
- Commerce 주문·결제 서버와 장애 및 자원 격리
- 근거 없는 추천과 Hallucination 방지
- 대용량 동시 연결 대응

---

# 2. 아키텍처

    사용자
    → Web Server / API Gateway
    → Chatbot API(Spring WebFlux + Netty)
    → 상품 Elasticsearch 검색
    → 사용자 질문 Embedding
    → 리뷰 Elasticsearch Hybrid Search
    → 사이즈별 통계 계산
    → RAG Prompt 생성
    → OpenAI LLM
    → SSE 토큰 스트리밍

## 서비스 경계

### Commerce API 책임

- 회원, 상품, 주문, 결제 및 리뷰 원본 관리
- ReviewOutboxEvent 생성
- Redis Stream 발행
- 리뷰 검색 문서 색인

### Chatbot API 책임

- JWT 검증
- 질문 분석
- 상품 및 ProductVariant 식별
- Query Embedding 생성
- Elasticsearch 검색
- 사이즈 추천 통계 계산
- RAG Prompt 생성
- LLM 스트리밍

### 금지

- Commerce MySQL 데이터 수정
- ReviewDocument 생성·수정·삭제
- 리뷰 원문 색인
- 주문·결제 비즈니스 로직 처리
- LLM을 통한 임의 사이즈 결정

---

# 3. 기술 스택

| 분류 | 기술 |
| --- | --- |
| Backend | Spring Boot, Spring WebFlux |
| Server | Reactor Netty |
| Reactive | Project Reactor |
| Search | Reactive Elasticsearch Client |
| Cache | Reactive Redis |
| AI | Spring AI, OpenAI Embedding, OpenAI LLM |
| Resilience | Resilience4j |
| Security | Spring Security WebFlux, JWT |
| Streaming | SSE |
| Monitoring | Micrometer, Prometheus, Grafana |
| Test | JUnit5, Reactor Test, WebTestClient, K6 |

---

# 4. 빌드 및 테스트

    ./gradlew clean build
    ./gradlew test
    ./gradlew compileJava
    ./gradlew bootRun
    ./gradlew test --tests "com.sh.aicommerce.chatbot.*"

---

# 5. 패키지 구조

    com.sh.aicommerce.chatbot
    ├── controller
    ├── application
    ├── analysis
    ├── product
    ├── retrieval
    ├── recommendation
    ├── answer
    ├── prompt
    ├── client
    ├── security
    ├── config
    ├── dto
    ├── exception
    ├── monitoring
    └── common

## 패키지 책임

- controller: 요청 검증과 SSE 응답만 담당한다.
- application: 질문 분석, 검색, 추천 및 답변 생성 흐름을 조합한다.
- analysis: 사용자 질문 의도와 검색 조건을 추출한다.
- product: ProductVariant를 식별하고 후보 중복을 처리한다.
- retrieval: Elasticsearch 검색과 Evidence 변환을 담당한다.
- recommendation: 사이즈별 통계와 추천 가능 여부를 계산한다.
- answer: RAG Prompt 생성, LLM 호출 및 토큰 스트리밍을 담당한다.

Controller에 비즈니스 로직을 작성하지 않는다.

---

# 6. Reactive 핵심 규칙

## Event Loop Blocking 금지

Netty Event Loop에서는 다음 작업을 실행하지 않는다.

- JPA/JDBC
- 동기 ElasticsearchRepository
- 동기 RedisTemplate
- RestTemplate
- 파일 I/O
- Thread.sleep()
- 외부 프로세스 실행
- 동기 LLM 호출

## 금지 API

    mono.block()
    flux.blockFirst()
    flux.blockLast()
    chatClient.prompt().call()

Service 내부에서 직접 subscribe()를 호출하지 않는다. Subscription은 WebFlux와 Reactor
런타임이 관리한다.

Controller와 Service는 원칙적으로 Mono 또는 Flux를 반환한다.

---

# 7. Blocking 작업 격리

불가피하게 Blocking 라이브러리를 사용하면 전용 bounded Scheduler로 격리한다.

    Mono.fromCallable(() -> blockingService.execute())
        .subscribeOn(chatbotBlockingScheduler);

Mono.just(blockingService.execute())는 Mono 생성 전에 Blocking 호출이 실행되므로 금지한다.

## Scheduler 규칙

- 공용 boundedElastic을 무제한으로 남용하지 않는다.
- 챗봇 전용 Scheduler를 사용한다.
- 최대 Thread와 Queue 크기를 제한한다.
- Queue가 가득 차면 무한 대기시키지 않는다.
- Scheduler Thread 수는 DB 및 외부 Connection Pool 용량과 함께 결정한다.

---

# 8. Reactive 데이터 접근

## Elasticsearch

사용:

- ReactiveElasticsearchOperations
- ReactiveElasticsearchRepository

Event Loop에서 동기 ElasticsearchRepository를 호출하지 않는다.

## Redis

사용:

- ReactiveStringRedisTemplate
- ReactiveRedisTemplate

Event Loop에서 StringRedisTemplate 또는 RedisTemplate을 호출하지 않는다.

## MySQL

Chatbot API는 원칙적으로 Commerce MySQL을 직접 조회하거나 수정하지 않는다. 불가피한
Reactive DB 접근은 R2DBC를 검토한다.

Event Loop에서 JpaRepository, EntityManager, JdbcTemplate을 사용할 수 없다.

---

# 9. 트랜잭션 규칙

LLM 호출과 SSE 스트리밍 중 DB 트랜잭션을 유지하지 않는다.

    금지:
    DB 트랜잭션 시작
    → Elasticsearch 검색
    → OpenAI 호출
    → SSE 스트리밍
    → 트랜잭션 종료

    허용:
    필요한 데이터 조회
    → DTO 변환
    → 조회 컨텍스트 종료
    → OpenAI 호출
    → SSE 스트리밍

Reactive DB 트랜잭션이 필요하면 TransactionalOperator를 사용한다. 일반 JPA의
Transactional과 Reactor 체인을 혼합하지 않는다.

---

# 10. SSE 스트리밍 규칙

## 이벤트 타입

    START
    TOKEN
    SOURCE
    COMPLETE
    ERROR

## 종료 규칙

    정상:
    TOKEN...
    → COMPLETE
    → 연결 종료

    실패:
    TOKEN...
    → ERROR
    → 연결 종료

- 무한 Stream을 만들지 않는다.
- 최대 연결시간을 설정한다.
- Client 취소 시 외부 LLM 요청도 취소한다.
- 느린 Client의 무제한 Buffering을 금지한다.
- 내부 예외 메시지를 사용자에게 직접 반환하지 않는다.

---

# 11. Chatbot 처리 Flow

    ChatRequest
    → 요청 검증
    → 질문 의도 분석
    → 상품/ProductVariant 식별
    → Query Embedding 생성
    → Review ES Hybrid Search
    → 리뷰 통계 계산
    → 추천 가능 여부 판단
    → RAG Prompt 생성
    → LLM 스트리밍
    → 응답 검증

각 단계는 별도 Component로 분리한다. 하나의 Service 메서드에 전체 로직을 작성하지
않는다.

---

# 12. 상품 식별 정책

- 상품 상세 페이지에서는 전달된 productId 또는 productVariantId를 검증한다.
- 일반 질문에서는 상품명으로 후보를 검색한다.
- 후보가 한 개면 진행한다.
- 후보가 여러 개면 사용자에게 선택을 요청한다.
- 상품을 찾지 못하면 임의로 생성하지 않는다.
- ProductVariant가 확정되기 전에는 리뷰 검색을 실행하지 않는다.

---

# 13. 리뷰 검색 정책

    productVariantId 필수 필터
    heightCm 숫자 범위
    weightKg 숫자 범위
    fitPreference 가중치
    contentEmbedding 벡터 유사도

## 금지

- 키와 몸무게를 벡터 검색 문자열에 포함
- 다른 ProductVariant 리뷰 혼합
- 삭제 또는 제외 리뷰 사용
- 회원 식별정보 검색
- 리뷰 원문 전체를 LLM에 전달
- 근거 리뷰 수 제한 없이 Prompt에 포함

---

# 14. 추천 정책

LLM이 추천 사이즈를 직접 결정하지 않는다. 애플리케이션이 다음 값을 먼저 계산한다.

    유사 리뷰 수
    사이즈별 구매 수
    착용감 분포
    핏 선호 일치 수
    정사이즈 후보
    오버핏 후보
    추천 신뢰도

LLM은 계산 결과를 자연어로 설명한다. 리뷰 근거가 부족하면 추천하지 않는다.

---

# 15. LLM 및 Prompt Injection 규칙

## 허용

- 검색된 상품 정보 설명
- 리뷰 통계 설명
- 계산된 추천 결과의 자연어 변환
- 참고 정보와 제한 사항 안내

## 금지

- 존재하지 않는 상품 또는 사이즈 생성
- 검색 근거가 없는 추천
- 시스템 Prompt 노출
- 내부 예외와 검색 Query 노출
- 사용자 지시로 시스템 정책 변경
- 의료 또는 건강 관련 판단

사용자 입력은 명령이 아니라 데이터로 취급한다. Prompt는 다음 영역을 구분한다.

    SYSTEM POLICY
    VERIFIED PRODUCT DATA
    VERIFIED REVIEW EVIDENCE
    USER QUESTION
    OUTPUT FORMAT

---

# 16. 개인정보 규칙

다음 데이터는 현재 요청 처리에만 사용한다.

- 사용자 키
- 사용자 몸무게
- 핏 선호

금지:

- 회원 프로필 저장
- 대화 이력 저장
- Embedding 입력에 구조화 신체정보 포함
- 일반 로그 또는 Metric Tag에 기록
- LLM 요청·응답 원문 로그 저장

로그에는 익명 requestId와 처리 결과 코드만 사용한다.

---

# 17. 오류 처리

반드시 Custom Exception과 안전한 오류 코드를 사용한다.

    INVALID_CHAT_REQUEST
    PRODUCT_NOT_FOUND
    AMBIGUOUS_PRODUCT
    INSUFFICIENT_REVIEW_DATA
    QUERY_EMBEDDING_FAILED
    ELASTICSEARCH_SEARCH_FAILED
    LLM_TIMEOUT
    LLM_RATE_LIMITED
    LLM_CIRCUIT_OPEN
    UNKNOWN_CHATBOT_ERROR

내부 예외 메시지를 SSE 응답에 직접 포함하지 않는다.

---

# 18. Timeout, Retry, Circuit Breaker

각 단계별 timeout을 분리한다.

    질문 분석
    상품 검색
    Query Embedding
    리뷰 검색
    LLM 최초 토큰
    LLM 전체 응답

Retry는 일시적인 오류에만 제한적으로 적용한다.

금지:

- 모든 예외 무조건 Retry
- 무제한 Retry
- 사용자 입력 오류 Retry
- 전체 LLM Stream 자동 재실행
- Retry Storm을 유발하는 구현

외부 서비스별 Circuit Breaker를 분리한다.

    OpenAI Query Embedding
    OpenAI Chat
    Elasticsearch
    Redis

LLM 동시 요청 수는 Bulkhead로 제한한다.

---

# 19. Backpressure

- 무제한 onBackpressureBuffer를 금지한다.
- Buffer 크기를 명시한다.
- 느린 Client 처리 정책을 정의한다.
- Queue 초과 시 안전하게 연결을 종료한다.
- 대량 리뷰를 한 번에 메모리에 적재하지 않는다.
- 검색 결과 수가 제한되지 않은 상태에서 collectList를 사용하지 않는다.

---

# 20. 로깅 및 모니터링

로그 금지 데이터:

- 질문 및 답변 원문
- 키와 몸무게
- 리뷰 원문
- JWT와 API Key
- Embedding Vector
- 시스템 Prompt

필수 Metric:

    현재 SSE 연결 수
    요청 성공·실패 수
    응답시간과 첫 토큰 응답시간
    OpenAI 호출시간과 오류율
    ES 검색시간
    검색된 리뷰 수
    추천 불가 비율
    Circuit Breaker 상태
    Bulkhead 사용량
    Scheduler Queue
    Netty Event Loop 지연

Metric Tag에 질문 원문과 개인정보를 넣지 않는다.

---

# 21. 테스트

## 단위 테스트

- 질문 분석
- 상품 후보 식별
- 리뷰 검색 조건
- 사이즈 통계 계산
- 데이터 부족 판단
- Prompt 생성
- LLM 응답 검증

## Reactive 테스트

- Reactor 흐름은 StepVerifier로 검증한다.
- Controller는 WebTestClient로 검증한다.
- Service 내부에서 테스트 편의를 위한 block을 추가하지 않는다.

## 필수 장애 테스트

- ES timeout
- OpenAI timeout과 429
- Circuit Open
- Client SSE 연결 취소
- 느린 Client
- 리뷰 데이터 부족
- 상품 후보 중복
- Prompt Injection

K6로 동시 SSE 연결, 첫 토큰 응답시간, 연결 유지시간, 오류율과 메모리 사용량을
검증한다.

---

# 22. 성능 기준

| 항목 | 목표 |
| --- | --- |
| 상품 식별 | 1초 이하 |
| 리뷰 검색 | 2초 이하 |
| 첫 토큰 응답 | 5초 이하 |
| 전체 RAG 응답 | 20초 이하 |
| 챗봇 오류율 | 1% 이하 |
| Event Loop Blocking | 0건 |
| 주문·결제 서비스 영향 | 없음 |

구체적인 동시 연결 수는 K6 측정 후 확정한다.

---

# 23. 기획안 확인 절차

기능 구현과 코드 리뷰 전에 반드시 다음 순서로 문서를 확인한다.

1. planning/specs/feature.toml을 읽는다.
2. 요청 기능과 일치하는 기획 문서를 찾는다.
3. 챗봇 기능은 다음 문서를 모두 확인한다.

   review-rag-chatbot.md
   review-rag-chatbot-architecture.md
   review-embedding-pipeline.md

4. 요구사항과 코드가 다르면 기획서 근거를 명시한다.
5. 기획서가 모호하거나 상충하면 최신 결정을 사용자에게 확인한다.
6. 기획 문서가 없으면 임의로 구현하지 않는다.

---

# 24. 코드 리뷰 우선순위

## P0

- 애플리케이션 기동 실패
- Event Loop Blocking
- JWT 우회
- 시스템 Prompt 노출
- 개인정보 로그 출력
- 근거 없는 추천
- 무제한 Stream 또는 메모리 증가

## P1

- LLM timeout 누락
- Circuit Breaker 누락
- ES 검색 결과 정합성 문제
- 삭제 리뷰 사용
- RAG 근거와 최종 답변 불일치
- SSE 연결 누수
- Backpressure 미처리

## P2

- 테스트 부족
- 불필요한 LLM 호출
- 중복 Query Embedding
- Scheduler 격리 부족
- 유지보수성 저하

## P3

- 네이밍
- 가독성
- 코드 스타일

리뷰 결과는 다음으로 분류한다.

    병합 가능 여부
    반드시 수정 필요 항목
    추가 검증 필요 항목

---

# 25. 금지 규칙 요약

    block()
    Service 내부 subscribe() 직접 호출
    Thread.sleep()
    RestTemplate
    Event Loop에서 동기 JPA/Elasticsearch/Redis 호출
    Event Loop에서 파일 I/O
    트랜잭션 내부 LLM 호출
    LLM을 통한 사이즈 직접 결정
    질문·답변·신체정보 로그
    무제한 Retry
    무제한 Buffer
    Entity 직접 반환

---

# 26. 최종 원칙

코드 에이전트는 다음 우선순위를 따른다.

1. 근거 정합성
2. 개인정보 보호
3. Event Loop 안정성
4. 장애 격리
5. 성능
6. 확장성
7. 가독성

위 원칙을 위반하는 구현은 수행하지 않는다.
