<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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

<link href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css"
	rel="stylesheet">

<link href="signin.css" rel="stylesheet">
<script src="../../assets/js/ie-emulation-modes-warning.js"></script>
<link href="./css/bootstrap.min.css" rel="stylesheet">
</head>

<body>

	<div class="container">
		<div class="row">
			<div class="col-md-4"></div>
			<div class="col-md-4">
				<form class="form-signin" action="register" method ="post" onSubmit="return checkForm()">
					<h2 class="form-signin-heading">Register Now!</h2>
					<label for="inputEmail" class="sr-only">Email address</label> <input
						type="text" id="inputEmail" name="username" class="form-control"
						placeholder="Username" required autofocus> 
				    
						<label for="inputPassword" class="sr-only">Password</label> <input
						type="password" id="inputPassword1" name="pwd" class="form-control"
						placeholder="Password" required>
						
						<label for="inputPassword" class="sr-only">Password</label> <input
						type="password" id="inputPassword2" name="pwd" class="form-control"
						placeholder="Password again" required>

						<div style="margin: 10px 0px 40px 0px">
							<span style="float:right"><a href="login-page.jsp">Have account?</a></span>
						</div>

					<button class="btn btn-lg btn-primary btn-block" type="submit">Register</button>
					
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
		var password = $('#inputPassword1').val();
		var passwordAgain = $('#inputPassword2').val();
		if (password == passwordAgain) {
			return true;
		} else {
			$('.error-item1 > p').html("两次密码输入不一致");
			$('.error-item1').show();
			$('#inputPassword1').val('');
			$('#inputPassword2').val('');
			return false;
		}
	}
</script>
</body>
</html>
