package sideproject.cutly.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sideproject.cutly.domain.ShortUrl;
import sideproject.cutly.dto.OriginalUrlDTO;
import sideproject.cutly.service.QrCodeService;
import sideproject.cutly.service.ShortUrlService;

import java.io.IOException;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ShortUrlController {

    private final ShortUrlService shortUrlService;
    private final QrCodeService qrCodeService;

    @Value("${shortenUrl}")
    private String shortenUrl;

    // 메인 페이지 (URL 입력 화면)
    @GetMapping("/")
    public String showHomePage() {
        return "home";
    }

    // URL 단축 처리
    @ResponseBody
    @PostMapping("/shorten")
    public String shortenUrl(@RequestBody OriginalUrlDTO dto) {
        String shortCode = shortUrlService.createShortUrl(dto.getOriginalUrl());

        return shortenUrl + shortCode;
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

    @ResponseBody
    @GetMapping("/qrcode")
    public void getQrCode(@RequestParam String url, HttpServletResponse response) throws IOException {
        byte[] qrCodeImage = qrCodeService.generateQrCode(url, 250, 250);
        response.setContentType("image/png");
        response.getOutputStream().write(qrCodeImage);
    }
}
