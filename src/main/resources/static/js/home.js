$.ajax({
    type : 'GET',
    url : '/user/home/data',
    dataType : 'json',
    success : function(data) {
        $('.username').text(data.username);
        if (data.subscribed === true && data.subscriptions.length > 0) {
            var sed = data.subscriptions.slice(-1).pop().getSubscriptionEndDate.split(' ')[0];
            $('.subscription_info').text(sed);
            $('.pre_subscribe').css("visibility", "hidden");
//            $('.subscription_info_prefix').removeClass('text-warning');
//            $('.subscription_info_prefix').addClass('text-info');
        } else {
            $('.subscription_info').text('');
            $('.pre_subscribe').css("visibility", "visible");
//            $('.subscription_info_prefix').removeClass('text-info');
//            $('.subscription_info_prefix').addClass('text-warning');
            $('.subscription_info_prefix').text("Підписка не оформлена");
        }
    }
});

$.ajax({
    type : 'GET',
    url : '/user/home/films',
    dataType : 'json',
    success : function(films) {
        console.log(films);
        $.each(films, function(k, v) {
        console.log(k, v);
            $('.film_container').append(
            '<div class="col-md-4">' +
              '<div class="card mb-4 box-shadow">' +
                '<img class="card-img-top" data-src="../images/films/'+v.id+'.jpg alt="Card image cap">' +
                '<div class="card-body">' +
                  '<p class="card-text">'+v.title+'</p>' +
                  '<div class="d-flex justify-content-between align-items-center">' +
                    '<div class="btn-group">' +
                      '<button type="button" class="btn btn-sm btn-outline-secondary">View</button>' +
                    '</div>' +
                    '<small class="text-muted">9 mins</small>' +
                  '</div>' +
                '</div>' +
              '</div>' +
            '</div>'
            );
        });
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
