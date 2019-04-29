$.ajax({
	type : 'GET',
	url : '/user/home/topics/closed',
	dataType : 'json',
	success : function(data) {
		if (data.length > 0) {
			$('<table class="closedTopics table-dark table-bordered"></table>').appendTo('.ctpc');
			$('<thead class="bg-info"></thead>').appendTo('.closedTopics');
			$('<tbody></tbody>').appendTo('.closedTopics');
			$('.closedTopics thead').append('<tr class="head"><th>Topics</th><th>Created dates</th><th>End dates</th><th>Creator</th><th>Bets</th></tr>');
			for (var i = 0; i < data.length; i++) {
				$('.closedTopics tbody').append('<tr class="cont"><td class="tdtitle">'+data[i].title+'</td><td>'+data[i].createdDate+'</td><td class="cdate">'+data[i].closedDate+'</td><td>'+data[i].createdBy+'</td><td class="betAmount" style="color:red;border-color:black">2₴</td></tr>');
			}
		} else {
			$('<p>There is no closed topics yet...</p>').appendTo('.ctpc');
		}
		
	}
});