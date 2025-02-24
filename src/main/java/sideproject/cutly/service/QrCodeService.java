package sideproject.cutly.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Service
public class QrCodeService {

    /**
     * 주어진 URL을 기반으로 지정된 크기의 QR 코드를 생성하여 바이트 배열로 반환합니다.
     *
     * @param url QR 코드로 생성할 URL
     * @param width QR 코드의 너비 (픽셀 단위)
     * @param height QR 코드의 높이 (픽셀 단위)
     * @return 생성된 QR 코드의 PNG 이미지 바이트 배열
     * @throws RuntimeException QR 코드 생성 중 오류 발생 시 예외 발생
     */
    public byte[] generateQrCode(String url, int width, int height) {
        try {
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

            BitMatrix bitMatrix = new MultiFormatWriter().encode(
                    url, BarcodeFormat.QR_CODE, width, height, hints);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("QR 코드 생성 실패", e);
        }
    }

    /**
     * 주어진 URL을 기반으로 지정된 크기의 QR 코드를 생성하여 파일로 저장합니다.
     *
     * @param url QR 코드로 생성할 URL
     * @param width QR 코드의 너비 (픽셀 단위)
     * @param height QR 코드의 높이 (픽셀 단위)
     * @param filePath 생성된 QR 코드를 저장할 파일 경로 (예: "output/qr_code.png")
     * @throws RuntimeException QR 코드 생성 또는 파일 저장 중 오류 발생 시 예외 발생
     */
    public void saveQrCodeToFile(String url, int width, int height, String filePath) {
        try {
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

            BitMatrix bitMatrix = new MultiFormatWriter().encode(
                    url, BarcodeFormat.QR_CODE, width, height, hints);

            Path path = FileSystems.getDefault().getPath(filePath);
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
        } catch (Exception e) {
            throw new RuntimeException("QR 코드 파일 저장 실패", e);
        }
    }
}
