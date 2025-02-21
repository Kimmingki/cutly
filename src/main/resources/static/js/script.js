// URL 단축 폼 제출 시 결과 표시 (모의 데이터 사용)
document.getElementById('shortenForm').addEventListener('submit', function (event) {
    event.preventDefault();

    const originalUrl = this.originalUrl.value;
    const shortUrl = `https://cut.ly/${Math.random().toString(36).substr(2, 6)}`;

    document.getElementById('shortUrl').textContent = shortUrl;
    document.getElementById('shortUrl').href = shortUrl;
    document.getElementById('shortenResult').style.display = 'block';
});

// QR 코드 생성 폼 제출 시 결과 표시 (모의 QR 코드 사용)
document.getElementById('qrForm').addEventListener('submit', function (event) {
    event.preventDefault();

    const url = this.url.value;
    const qrCodeApi = `https://api.qrserver.com/v1/create-qr-code/?size=200x200&data=${encodeURIComponent(url)}`;

    document.getElementById('qrCodeImage').src = qrCodeApi;
    document.getElementById('qrCodeResult').style.display = 'block';
});