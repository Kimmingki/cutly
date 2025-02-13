package sideproject.cutly.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sideproject.cutly.domain.ShortUrl;
import sideproject.cutly.service.ShortUrlService;

import java.io.IOException;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ShortUrlController {

    private final ShortUrlService shortUrlService;

    // 메인 페이지 (URL 입력 화면)
    @GetMapping("/")
    public String showHomePage() {
        return "home";
    }

    // URL 단축 처리
    @PostMapping("/shorten")
    public String shortenUrl(@RequestParam("originalUrl") String originalUrl, Model model) {
        String shortCode = shortUrlService.createShortUrl(originalUrl);
        model.addAttribute("shortUrl", "http://localhost:8080/" + shortCode);

        return "result";
    }

    // 단축 URL 리다이렉트
    @GetMapping("/{shortCode}")
    public void redirectToOriginalUrl(@PathVariable("shortCode") String shortCode, HttpServletResponse response) throws IOException {
        Optional<ShortUrl> shortUrlOptional = shortUrlService.getShortUrl(shortCode);

        if (shortUrlOptional.isPresent()) {
            ShortUrl shortUrl = shortUrlOptional.get();
            shortUrlService.incrementClickCount(shortCode);
            response.sendRedirect(shortUrl.getOriginalUrl());
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "ShortUrl not found");
        }
    }
}
