package sideproject.cutly.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "short_url")
public class ShortUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                                // 기본 키

    @Column(nullable = false, length = 2048)
    private String originalUrl;                     // 원본 URL

    @Column(nullable = false, unique = true, length = 10)
    private String shortCode;                       // 단축 URL 코드

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;                // 생성 시간

    @Column(nullable = false)
    private Integer clickCount = 0;                 // 클릭 수

    @Builder
    public ShortUrl(String originalUrl, String shortCode) {
        this.originalUrl = originalUrl;
        this.shortCode = shortCode;
    }

    public void setClickCount(Integer clickCount) {
        this.clickCount = clickCount;
    }
}
