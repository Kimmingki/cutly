package sideproject.cutly.utils;

import org.springframework.beans.factory.annotation.Value;

import java.util.Random;

public class Base62Encoder {

    @Value("${base62}")
    private static String BASE62;
    private static final Random RANDOM = new Random();

    // 숫자를 Base62 문자열로 변환
    public static String encode(long value) {
        StringBuilder sb = new StringBuilder();
        while (value > 0) {
            sb.insert(0, BASE62.charAt((int) (value % 62)));
            value /= 62;
        }

        return sb.toString();
    }

    // 랜덤한 Base62 문자열 생성 (최대 10자리)
    public static String generateRandomCode() {
        StringBuilder sb = new StringBuilder(10);
        for (int i = 0; i < 8; i++) {
            sb.append(BASE62.charAt(RANDOM.nextInt(62)));
        }

        return sb.toString();
    }
}
