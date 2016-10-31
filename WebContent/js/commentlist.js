
//提交表单时加载
function addComment() {
	$.ajax({
		type : "POST",
		url : "AddComment",
		async : false,
		cache : false,
		data : {
			comment : $('#newComment').val(),
			weiboid : $('#weiboid').text(),
		},
		dataType : 'html',
		success : function(data) {
			console.log(JSON.stringify(data));
			$('#comment').html(data);
			reset();
		},
		error : function() {
			console.log("error");
		}
	});

	function reset() {
		$('#newComment').val("");
	}
}

//分页ajax实现
function paginate(i) {
	$.ajax({
		type : "POST",
		url : "CommentList",
		async : false,
		cache : false,
		data : {
			weiboid : $('#weiboid').text(),
			page : i,
			commentcount : $('#commentcount').text(),
		},
		dataType : 'html',
		success : function(data) {
			$('#comment').html(data);
		},
		error : function() {
			alert("error");
		}
	});
}

//删除的时候加载
function deleteComment(i) {
	$.ajax({
		type : "POST",
		url : "DeleteComment",
		async : false,
		cache : false,
		data : {
			weiboid : $('#weiboid').text(),
			commentid : i
		},
		dataType : 'html',
		success : function(data) {
			$('#comment').html(data);
			reset();
		},
		error : function() {
			alert("error");
		}
	});
}