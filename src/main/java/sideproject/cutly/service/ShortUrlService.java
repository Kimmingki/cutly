package sideproject.cutly.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sideproject.cutly.domain.ShortUrl;
import sideproject.cutly.repository.ShortUrlRepository;
import sideproject.cutly.utils.Base62Encoder;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ShortUrlService {

    private final ShortUrlRepository shortUrlRepository;
    private final StringRedisTemplate redisTemplate;

    private static final String REDIS_PREFIX = "shortUrl:";

    /**
     * 원본 URL을 단축 URL로 생성하여 저장합니다.
     *
     * <p>단계별 동작:
     * <ol>
     *   <li>랜덤 방식으로 단축 코드를 생성합니다.</li>
     *   <li>생성된 단축 코드가 중복되지 않도록 검사하며, 중복 시 새로운 코드를 생성합니다.</li>
     *   <li>단축 코드와 원본 URL을 ShortUrl 엔티티로 생성하여 데이터베이스에 저장합니다.</li>
     *   <li>생성된 단축 URL 정보를 Redis에 1일 동안 캐싱합니다.</li>
     * </ol>
     *
     * @param originalUrl 단축할 원본 URL
     * @return 생성된 단축 코드
     */
    @Transactional
    public String createShortUrl(String originalUrl) {
        // 단축 코드 생성 (랜덤 방식)
        String shortCode = Base62Encoder.generateRandomCode();

        // 중복 검사 후 새로운 단축 코드 생성
        while (shortUrlRepository.findByShortCode(shortCode) != null) {
            shortCode = Base62Encoder.generateRandomCode();
        }

        // ShortUrl 엔티티 저장
        ShortUrl shortUrl = ShortUrl.builder()
                .originalUrl(originalUrl)
                .shortCode(shortCode)
                .build();

        shortUrlRepository.save(shortUrl);
        redisTemplate.opsForValue().set(REDIS_PREFIX + shortCode, originalUrl, 1, TimeUnit.DAYS);
        return shortCode;
    }

    /**
     * 단축 URL 정보를 조회합니다.
     *
     * <p>먼저 Redis 캐시에서 해당 단축 코드에 대한 원본 URL을 조회합니다.
     * 캐시에 존재하면 {@link ShortUrl} 객체를 생성하여 반환합니다.
     * 캐시에 없으면 데이터베이스에서 조회하며, 만약 단축 URL이 만료되었으면 삭제 후 빈 {@link Optional}을 반환합니다.
     *
     * @param shortCode 조회할 단축 URL 코드
     * @return 단축 URL 정보가 존재하면 {@link Optional}에 담아 반환하며, 없거나 만료 시 {@link Optional#empty()} 반환
     */
    @Transactional(readOnly = true)
    public Optional<ShortUrl> getShortUrl(String shortCode) {
        String cachedUrl = redisTemplate.opsForValue().get(REDIS_PREFIX + shortCode);
        if (cachedUrl != null) {
            return Optional.of(ShortUrl.builder().shortCode(shortCode).originalUrl(cachedUrl).build());
        }

        Optional<ShortUrl> shortUrlOptional = Optional.ofNullable(shortUrlRepository.findByShortCode(shortCode));

        if (shortUrlOptional.isPresent() && shortUrlOptional.get().getExpiresAt().isBefore(LocalDateTime.now())) {
            deleteExpiredUrl(shortCode);
            return Optional.empty();
        }

        return shortUrlOptional;
    }

    /**
     * 단축 URL의 클릭 수를 1 증가시킵니다.
     *
     * @param shortCode 클릭 수를 증가시킬 단축 URL의 코드
     */
    @Transactional
    public void incrementClickCount(String shortCode) {
        ShortUrl shortUrl = shortUrlRepository.findByShortCode(shortCode);
        if (shortUrl != null) {
            shortUrl.setClickCount(shortUrl.getClickCount() + 1);
        }
    }

    /**
     * 주어진 단축 URL 코드를 기준으로 만료된 URL 정보를 데이터베이스와 Redis 캐시에서 삭제합니다.
     *
     * @param shortCode 삭제할 단축 URL의 코드
     */
    @Transactional
    public void deleteExpiredUrl(String shortCode) {
        shortUrlRepository.deleteByShortCode(shortCode);
        redisTemplate.delete(REDIS_PREFIX + shortCode);
    }
}
