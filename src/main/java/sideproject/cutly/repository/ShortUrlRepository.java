package sideproject.cutly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sideproject.cutly.domain.ShortUrl;

import java.time.LocalDateTime;
import java.util.List;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {

    // 단축 코드로 URL 조회
    ShortUrl findByShortCode(String shortCode);

    // 만료된 코드 삭제
    void deleteByShortCode(String shortCode);

    @Query("select s.shortCode from ShortUrl s where s.expiresAt < :now")
    List<String> findExpiredUrls(LocalDateTime now);
}
