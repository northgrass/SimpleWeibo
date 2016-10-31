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

<link href="./css/bootstrap.min.css" rel="stylesheet">

<link href="signin.css" rel="stylesheet">

<script src="../../assets/js/ie-emulation-modes-warning.js"></script>

</head>

<body>

	<div class="container">
		<div class="row">
			<div class="col-md-4"></div>
			<div class="col-md-4">
				<form class="form-signin" action="login" method="post">
					<h2 class="form-signin-heading">Please sign in</h2>
					<label for="inputEmail" class="sr-only">Email address</label> 
					<input
						type="text" id="inputEmail" name="username" class="form-control"
						placeholder="Username" required autofocus> 
						<label
						for="inputPassword" class="sr-only">Password</label> 
						<input
						type="password" id="inputPassword" name="pwd" class="form-control"
						placeholder="Password" required>
					<div class="row">
						<div class="col-md-6">
							<div class="checkbox" style="margin: 10px 0px 10px 0px">
								<label> <input type="checkbox" value="remember-me">
									Remember me
								</label>
							</div>
						</div>

						<div class="col-md-6" style="margin: 10px 0px 10px 0px">
							<span style="float: right"><a href="register-page.jsp">Register?</a></span>
						</div>
					</div>

					<button class="btn btn-lg btn-primary btn-block" type="submit">Sign
						in</button>
					<div>
						<h4 style="color: red; font-family: 黑体">${requestScope.message}</h4>
					</div>


				</form>

			</div>
			<div class="col-md-4"></div>
		</div>
	</div>


	<script src="./js/jquery-3.1.0.min.js"></script>
	
	<script type="text/javascript">

</script>
</body>
</html>
