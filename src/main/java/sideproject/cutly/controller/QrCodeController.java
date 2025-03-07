package sideproject.cutly.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sideproject.cutly.service.QrCodeService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/qrcode")
public class QrCodeController {

    private final QrCodeService qrCodeService;

    @GetMapping
    public void getQrCode(@RequestParam String url, HttpServletResponse response) throws IOException {
        byte[] qrCodeImage = qrCodeService.generateQrCode(url, 250, 250);
        response.setContentType("image/png");
        response.getOutputStream().write(qrCodeImage);
    }
}
