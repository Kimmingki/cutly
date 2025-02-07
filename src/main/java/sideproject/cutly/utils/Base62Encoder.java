package sideproject.cutly.utils;

import org.springframework.beans.factory.annotation.Value;

import java.util.Random;

public class Base62Encoder {

    @Value("${base62}")
    private static String BASE62;
    private static final Random RANDOM = new Random();

    // 숫자를 Base62 문자열로 변환
    public static String encode(long value) {

    }
}
