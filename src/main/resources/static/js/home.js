var is_subscribed;
var is_empty;

$.ajax({
    type : 'GET',
    url : '/user/home/data',
    dataType : 'json',
    success : function(data) {
        $('.username').text(data.username);
        if (data.subscribed === true && data.subscriptions.length > 0) {
            is_subscribed = true;
            is_empty = false;
            var sed = data.subscriptions.slice(-1).pop().getSubscriptionEndDate.split(' ')[0];
            $('.subscription_info').text(sed);
            $('.pre_subscribe').css("visibility", "hidden");
        } else {
            is_subscribed = false;
            is_empty = true;
            $('.subscription_info').text('');
            $('.pre_subscribe').css("visibility", "visible");
            $('.subscription_info_prefix').text("Підписка не оформлена");
        }
    }
});


function play_click(arg) {
    if (is_empty === false && is_subscribed === true) {
        window.open("https://youtube.com/embed/"+arg);
    } else {
        alert('Оформіть будь ласка підписку!');
    }
}


$.ajax({
    type : 'GET',
    url : '/user/home/films',
    dataType : 'json',
    success : function(films) {
        if (films.length == 0) {
            $('.film_container').append('<div class="col-md-4"><h2>Контент відсутній</h2></div>');
        } else {
            $.each(films, function(k, v) {
                $('.film_container').append(
                '<div class="col-md-4">' +
                  '<div class="card mb-4 box-shadow">' +
                    '<img class="card-img-top" src="../../images/films/'+v.id+'.jpg"'+' alt="Card image cap">' +
                    '<div class="card-body">' +
                      '<h5>'+v.title+'</h5>' +
                      '<div class="d-flex justify-content-between align-items-center">' +
                        '<div class="btn-group">' +
                          '<button type="button" class="btn btn-sm btn-outline-primary play_btn" onClick="play_click(\''+v.link+'\')">PLAY</button>' +
                        '</div>' +
                        '<small class="text-muted">'+v.genre+'</small>' +
                      '</div>' +
                    '</div>' +
                  '</div>' +
                '</div>'
                );
            });
        }
    }
});

$('.search_btn').click(function() {
    $.ajax({
        type : 'POST',
        url : '/user/home/films/title',
        data : {
            title : $('.search_in').val()
        },
        success : function(films) {
            $('.film_container').empty();
            if (films.length === 0) {
                $('.film_container').append('<div class="col-md-4"><h2>Контент відсутній</h2></div>');
            } else {
                $.each(films, function(k, v) {
                    $('.film_container').append(
                    '<div class="col-md-4">' +
                      '<div class="card mb-4 box-shadow">' +
                        '<img class="card-img-top" src="../../images/films/'+v.id+'.jpg"'+' alt="Card image cap">' +
                        '<div class="card-body">' +
                          '<h5>'+v.title+'</h5>' +
                          '<div class="d-flex justify-content-between align-items-center">' +
                            '<div class="btn-group">' +
                              '<button type="button" class="btn btn-sm btn-outline-primary play_btn" onClick="play_click(\''+v.link+'\')">PLAY</button>' +
                            '</div>' +
                            '<small class="text-muted">'+v.genre+'</small>' +
                          '</div>' +
                        '</div>' +
                      '</div>' +
                    '</div>'
                    );
                });
            }
        }
    });
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
