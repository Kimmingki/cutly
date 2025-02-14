package sideproject.cutly.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sideproject.cutly.repository.ShortUrlRepository;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UrlCleanupService {

    private final ShortUrlRepository shortUrlRepository;

    @Transactional
    @Scheduled(cron = "0 0 3 * * ?")
    public void cleanupExpiredUrls() {
        List<String> expiredShortCodes = shortUrlRepository.findExpiredUrls(LocalDateTime.now());

        for (String shortCode : expiredShortCodes) {
            shortUrlRepository.deleteByShortCode(shortCode);
            log.info("만료된 단축 URL 삭제: {}", shortCode);
        }
    }
}
