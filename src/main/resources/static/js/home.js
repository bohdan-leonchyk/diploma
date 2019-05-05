$.ajax({
    type : 'GET',
    url : '/user/home/data',
    dataType : 'json',
    success : function(data) {
        $('.username').text(data.username);
        if (data.subscribed === true) {
            var sed = data.subscriptions.slice(-1).pop().getSubscriptionEndDate.split(' ')[0];
            $('.subscription_info').text(sed);
            $('.subscription_info_prefix').removeClass('text-warning');
            $('.subscription_info_prefix').addClass('text-info');
        } else {
            $('.subscription_info').text('');
            $('.subscription_info_prefix').removeClass('text-info');
            $('.subscription_info_prefix').addClass('text-warning');
            $('.subscription_info_prefix').text("Підписка не оформлена");
        }
    }
});

$('.subscribe').click(function() {
    if ($('.card_number').val().length == 16
            && $('.card_month').val().length == 2
            && $('.card_year').val().length == 2
            && $('.card_cvv').val().length == 3) {
        var val = $('input[name=subscription_type]:checked').val();
        if (val === "ANNUAL") {
            val = 100;
        } else if (val === "MONTHLY") {
            val = 10;
        }
        if (confirm('Ви впевнені, що бажаєте оформити підписку за ' + val + ' USD ?')) {
            $.ajax({
                type : 'POST',
                url : '/user/home/subscribe',
                data : {
                    subscriptionType : $('input[name=subscription_type]:checked').val()
                },
                success : function() {
                    location.reload();
                },
                error : function() {
                    alert('Помилка');
                }
            });
        }
    } else {
        alert("Будь ласка заповніть форму коректно.");
    }
});

$('.card_number, .card_month, .card_year, .card_cvv').on('input', function (event) {
    this.value = this.value.replace(/[^0-9]/g, '');
});

$('.radio_in').click(function() {
    var val = $('input[name=subscription_type]:checked').val();
    if (val === "ANNUAL") {
        $('.subscription_price').text('100');
    } else if (val === "MONTHLY") {
        $('.subscription_price').text('10');
    }
});
