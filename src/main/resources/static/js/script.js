$(document).ready(function () {
    // URL 단축 폼 처리
    $('#shortenBtn').on('click', function (e) {
        e.preventDefault();

        const originalUrl = $('input[name="originalUrl"]').val();

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

    // QR 코드 생성 폼 처리
    $('#qrForm').on('submit', function (e) {
        e.preventDefault();

        const url = $('#url').val();
        const qrCodeApi = `https://api.qrserver.com/v1/create-qr-code/?size=200x200&data=${encodeURIComponent(url)}`;

        $('#qrCodeImage').attr('src', qrCodeApi);
        $('#qrCodeResult').fadeIn();
    });

});