$(document).ready(function () {
    // URL 단축 폼 처리
    $('#shortenBtn').on('click', function (e) {
        e.preventDefault();

        const originalUrl = $("input[name='originalUrl']").val();

        $.ajax({
            type: 'POST',
            url: '/shorten',
            data: JSON.stringify({originalUrl}),
            contentType: 'application/json',
            success: function (response) {
                $('#shortUrl').text(response).attr('href', response);
                $('#shortenResult').fadeIn();
            },
            error: function () {
                alert('URL 단축에 실패했습니다. 다시 시도해주세요.');
            }
        });
    });
    
    // 클립보드 복사 기능
    $('#copyBtn').on('click', function () {
        const shortUrl = $('#shortUrl').attr('href');
        
        navigator.clipboard.writeText(shortUrl).then(() => {
            alert('단축 URL이 복사되었습니다.');
        }).catch(err => {
            alert('복사에 실패하였습니다.');
        });
    });
    
    // 웹 공유 API (모바일 및 지원 브라우저에서 작동)
    $('#shareBtn').on('click', function () {
        const shortUrl = $('#shortUrl').attr('href');
        
        if (navigator.share) {
            navigator.share({
                title: 'Cutly - 단축 URL',
                text: '단축된 URL을 공유합니다.',
                url: shortUrl
            }).then(() => {
                console.log('공유 성공');
            }).catch(err => {
                console.log('공유 취소 또는 실패: ', err);
            });
        } else {
            alert('현재 브라우저는 공유 기능을 지원하지 않습니다. 복사 후 직접 공유해주세요.');
        }
    });

    $('#qrForm').on('submit', function (e) {
        e.preventDefault();

        const url = $('#url').val();
        const qrCodeApi = `https://api.qrserver.com/v1/create-qr-code/?size=200x200&data=${encodeURIComponent(url)}`;

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

    // QR 코드 공유 기능 (웹 공유 API)
    $('#shareQrBtn').on('click', function () {
        const qrImage = document.getElementById('qrCodeImage').src;

        if (navigator.share) {
            navigator.share({
                title: 'Cutly - QR 코드',
                text: '이 QR 코드를 스캔하면 단축된 URL에 접속할 수 있습니다!',
                url: qrImage
            }).then(() => {
                console.log('QR 코드 공유 성공');
            }).catch(err => {
                console.log('QR 코드 공유 실패:', err);
            });
        } else {
            alert('현재 브라우저는 QR 코드 공유 기능을 지원하지 않습니다. 직접 공유해 주세요.');
        }
    });
});