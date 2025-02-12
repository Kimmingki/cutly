package sideproject.cutly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sideproject.cutly.domain.ShortUrl;

import java.util.List;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {

    // 단축 코드로 URL 조회
    ShortUrl findByShortCode(String shortCode);

    // 클릭 수가 많은 URL 상위 10개 조회
    List<ShortUrl> findTop10ByOrderByClickCountDesc();
}
