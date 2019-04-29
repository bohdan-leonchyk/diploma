//$('.datepicker').datepicker();

$.ajax({
	type : 'GET',
	url : '/user/home/data',
	dataType : 'json',
	success : function(data) {
		$('.username').text(data.username);
		$('.honor').text('Honor: '+data.honor);
		$('.balance').text('Balance: '+data.balance+'₴');
	}
});

$.ajax({
	type : 'GET',
	url : '/user/home/topics/active',
	dataType : 'json',
	success : function(data) {
		if (data.length > 0) {
//			$('<p>Active topics:</p>').appendTo('.atpc');
			$('<table class="activeTopics table-dark table-bordered"></table>').appendTo('.atpc');
			$('<thead class="bg-info"></thead>').appendTo('.activeTopics');
			$('<tbody></tbody>').appendTo('.activeTopics');
			$('.activeTopics thead').append('<tr class="head"><th class="numb">#</th><th>Topics</th><th>Answers</th><th>Created dates</th><th>End dates</th><th>Creator</th><th>Bets</th></tr>');
			for (var i = 0; i < data.length; i++) {
				$('.activeTopics tbody').append('<tr class="cont"><td class="numb">'+data[i].id+'</td><td class="tdtitle">'+data[i].title+'</td><td><select class="sel'+i+'"></select></td><td>'+data[i].createdDate+'</td><td class="cdate">'+data[i].closedDate+'</td><td>'+data[i].createdBy+'</td><td class="betAmount bg-warning text-dark">2₴</td><td><button class="submitAnswer btn btn-primary"><h1 class="text-success">₴</h1></button></td></tr>');
				for (var j = 0; j < data[i].answers.length; j++) {
					let numb = data[i].answers[j].id;
					$('.sel'+i).append('<option id="'+numb+'" value="'+data[i].answers[j].answer+'">'+data[i].answers[j].answer+'</option>');
				}
			}
		}
		
		$('.submitAnswer').click(function(){
			if (confirm("Are you sure you want to make a Ebabet?")) {
				$.ajax({
					type : 'POST',
					url : '/user/home/ebabet',
					data : {
						topic : parseInt($(this).parents('tr').find('.numb').text()),
						answer: $(this).parents('tr').find('select').val(),
						betAmount: parseInt($(this).parents('tr').find('.betAmount').text().match(/\d+/))
					},
					success : function() {
						location.reload();
					},
					error : function() {
						alert("За тобой уже выехали, читер.");
					}
				});
			}
		});
	}
});

$.ajax({
	type : 'GET',
	url : '/user/home/topics/answered',
	dataType : 'json',
	success : function(data) {
		if (data.length > 0) {
			$('<table class="answeredTopics table-dark table-bordered"></table>').appendTo('.antpc');
			$('<thead class="bg-info"></thead>').appendTo('.answeredTopics');
			$('<tbody></tbody>').appendTo('.answeredTopics');
			$('.answeredTopics thead').append('<tr class="head"><th>Topics</th><th>Answers</th><th>Answered Dates</th><th>End dates</th><th>Bets</th></tr>');
			for (var i = 0; i < data.length; i++) {
				$('.answeredTopics tbody').append('<tr class="cont"><td class="tdtitle">'+data[i].topic.title+'</td><td>'+data[i].answerValue+'</td><td>'+data[i].answeredDate+'</td><td>'+data[i].topic.closedDate+'</td><td class="bg-warning text-dark">'+data[i].betAmount+'₴</td></tr>');
			}
		}
		
	}
});

$('.ctView').click(function() {
	$(location).attr('href', '/user/closed_topics')
});

$('.createTopic').click(function() {
	if (confirm('Are you sure to create topic: "'+$('.topicTitle').val()+'" ?')) {
		$.ajax({
			type : 'POST',
			url : '/user/home/topics/create',
			data : {
				title : $('.topicTitle').val(),
				endDate : $('.topicEndDate').val()
			},
			success : function() {
				$('.topicMes').removeClass('bg-danger').addClass('bg-success').text('Success!');
				location.reload();
			},
			error : function() {
				$('.topicMes').removeClass('bg-success').addClass('bg-danger').text('Wrong date or emty Topic title');
			}
		});
	}
});
