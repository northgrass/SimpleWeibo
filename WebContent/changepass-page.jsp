<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="icon" href="../../favicon.ico">
<title>Signin Template for Bootstrap</title>
<link href="./css/bootstrap.min.css" rel="stylesheet">
<link href="signin.css" rel="stylesheet">
<script src="../../assets/js/ie-emulation-modes-warning.js"></script>

<style type="text/css">
body, ul, li {
	margin: 0;
	padding: 0;
	font-style: normal;
	font: 12px/22px "\5B8B\4F53", Arial, Helvetica, sans-serif
}

ol, ul, li {
	list-style: none
}

body {
	color: #000000;
	background: #FFF;
	text-align: center
}

.clear {
	clear: both;
	height: 1px;
	width: 100%;
	overflow: hidden;
	margin-top: -1px
}

a {
	color: #000000;
	text-decoration: none
}

a:hover {
	color: #BA2636
}

.red, .red a {
	color: #F00
}

.lan, .lan a {
	color: #1E51A2
}

ul#nav {
	width: 100%;
	height: 60px;
	background: #00A2CA;
	margin: 0 auto
}

ul#nav li {
	display: inline;
	height: 60px
}

ul#nav li a {
	display: inline-block;
	padding: 0 20px;
	height: 60px;
	line-height: 60px;
	color: #FFF;
	font-family: "\5FAE\8F6F\96C5\9ED1";
	font-size: 16px
}

ul#nav li a:hover {
	background: #0095BB
}
</style>

</head>

<body>

	<ul id="nav">
		<li><a href="./HotWeibo">首页</a></li>
		<li><a href="./WeiboList">关注微博</a></li>
		<li><a href="search-page.jsp">用户搜索</a></li>
		<li><a href="changepass-page.jsp">修改密码</a></li>
		<li><a href="Quit?flag=quit">退出登录</a></li>
	</ul>

	<div class="container">
		<div class="row">
			<div class="col-md-4"></div>
			<div class="col-md-4">
				<form class="form-signin" action="changepass" method="post">
					<h2 class="form-signin-heading">Change Password!</h2>

					<label for="inputOldPassword" class="sr-only">Password</label> <input
						type="password" id="inputOldPassword" name="oldpwd"
						class="form-control" placeholder="Old Password" required>

					<label for="inputNewPassword" class="sr-only">Password</label> <input
						type="password" id="inputNewPassword" name="newpwd1"
						class="form-control" placeholder="New Password" required>

					<label for="inputNewPasswordAgain" class="sr-only">Password</label>
					<input type="password" id="inputNewPasswordAgain" name="newpwd2"
						class="form-control" placeholder="New Password again" required>

					<button class="btn btn-lg btn-primary btn-block" type="submit"
						style="margin: 10px 0px 40px 0px" onSubmit="return checkForm()">Change</button>

					<div>
						<h4 style="color: red; font-family: 黑体">${requestScope.message}</h4>
					</div>
					<div class="error-item1" style="display:none">
		        <p  style="color:red; font-size:18px"></p>
		    </div>
				</form>
			</div>
			<div class="col-md-4"></div>
		</div>
	</div>
	<!-- /container -->
	<script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>
		<script src="./js/jquery-3.1.0.min.js"></script>
	
	<script type="text/javascript">
	function checkForm(){
		var password = $('#inputNewPassword').val();
		var passwordAgain = $('#inputNewPasswordAgain').val();
		if (password == passwordAgain) {
			return true;
		} else {
			$('.error-item1 > p').html("两次密码输入不一致");
			$('.error-item1').show();
			$('#inputNewPassword').val('');
			$('#inputNewPasswordAgain').val('');
			return false;
		}
	}
</script>
</body>
</html>
