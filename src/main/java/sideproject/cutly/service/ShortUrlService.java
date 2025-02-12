package sideproject.cutly.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sideproject.cutly.domain.ShortUrl;
import sideproject.cutly.repository.ShortUrlRepository;
import sideproject.cutly.utils.Base62Encoder;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShortUrlService {

    private final ShortUrlRepository shortUrlRepository;

    // 단축 URL 생성
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
        return shortCode;
    }

    // 단축 URL 조회
    @Transactional(readOnly = true)
    public Optional<ShortUrl> getShortUrl(String shortCode) {
        return Optional.ofNullable(shortUrlRepository.findByShortCode(shortCode));
    }

    // 단축 URL 클릭 수 증가
    @Transactional
    public void incrementClickCount(String shortCode) {
        ShortUrl shortUrl = shortUrlRepository.findByShortCode(shortCode);
        if (shortUrl != null) {
            shortUrl.setClickCount(shortUrl.getClickCount() + 1);
        }
    }
}
