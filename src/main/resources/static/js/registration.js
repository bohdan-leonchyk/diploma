$('.registration').click(function() {
	$(location).attr('href', '/registration');
});

$('.register').click(function() {
	$.ajax({
		type : 'POST',
		url : '/registration',
		data : {
			username : $('.username').val(),
			password : $('.password').val()
		},
		success : function() {
			$('.mess').css('display', 'block').removeClass('bg-danger').addClass('bg-success');
			$('.status').text('Успішно зареєстровано');
			$('.register').css('display', 'none');
		},
		error : function() {
			$('.mess').css('display', 'block').removeClass('bg-success').addClass('bg-danger');
			$('.status').text('Неможливо зареєструватись');
		}
	});
});

$('.bmp').click(function() {
	$(location).attr('href', '/');
});