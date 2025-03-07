$(document).ready(function () {
    $('#qrForm').on('submit', function (e) {
        e.preventDefault();

        const url = $('#url').val();
        const qrCodeApi = `https://api.qrserver.com/v1/create-qr-code/?size=200x200&data=${encodeURIComponent(url)}`;
        // const qrCodeApi = `/qrcode/generate?url=${encodeURIComponent(url)}`;

        // 로딩 아이콘 표시 및 기존 QR 코드 숨김
        $('#qrCodeResult').fadeIn();
        $('#qrLoading').show();
        $('#qrCodeImage').hide();

        // QR 코드 이미지 로드
        const qrImg = new Image();
        qrImg.src = qrCodeApi;
        qrImg.onload = function () {
            $('#qrLoading').hide();  // 로딩 아이콘 숨김
            $('#qrCodeImage').attr('src', qrCodeApi).fadeIn(); // QR 코드 표시
        };
    });

    // QR 코드 클립보드 복사 기능
    $('#copyQrBtn').on('click', function () {
        const qrImage = document.getElementById('qrCodeImage');

        fetch(qrImage.src)
            .then(response => response.blob())
            .then(blob => {
                navigator.clipboard.write([
                    new ClipboardItem({ "image/png": blob })
                ]).then(() => {
                    alert('QR 코드가 클립보드에 복사되었습니다!');
                }).catch(err => {
                    console.error('QR 코드 복사 실패:', err);
                    alert('QR 코드 복사에 실패했습니다.');
                });
            });
    });

    // QR 코드 다운로드 기능 (Blob 방식)
    $('#downloadQrBtn').on('click', function () {
        const qrImage = document.getElementById('qrCodeImage').src;

        fetch(qrImage)
            .then(response => response.blob())
            .then(blob => {
                const link = document.createElement('a');
                link.href = URL.createObjectURL(blob);
                link.download = 'qrcode.png';  // 파일 이름 설정
                document.body.appendChild(link);
                link.click();
                document.body.removeChild(link);
            })
            .catch(err => console.error('QR 코드 다운로드 실패:', err));
    });
});