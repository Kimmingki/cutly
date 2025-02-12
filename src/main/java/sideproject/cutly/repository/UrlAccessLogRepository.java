package sideproject.cutly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sideproject.cutly.domain.UrlAccessLog;

import java.util.List;

public interface UrlAccessLogRepository extends JpaRepository<UrlAccessLog, Long> {

    // 특정 단축 URL의 접근 로그 조회
    List<UrlAccessLog> findByShortUrlId(Long shortUrlId);
}
