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
});