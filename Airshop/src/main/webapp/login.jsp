<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div style="margin-left: 15px" class="panel">
<form action="login" method="POST">
	<div class="row">
		<div align="center" class="large-12 columns">
			<h3>Authorization</h3>
			<c:if test="${incorrect_credentionals}">
				<small id='userExistError' class='error' style='margin-top: 0px;'>Incorrect
					login or password. Please try again!</small>
			</c:if>
			<c:if test="${banned}">
            				<small id='bannedError' class='error' style='margin-top: 0px;'>You have been banned, please wait!</small>
            			</c:if>
		</div>
	</div>
	<div class="row">
		<div style="margin-top: 15px" class="large-12 large-centered columns">
			<input id='Email' style="margin-bottom: 0px;" value="${email}"
				name='login' type="text" placeholder="Login">
		</div>
	</div>

	<div class="row">
		<div style="margin-top: 15px" class="large-12 large-centered columns">
			<input id='Password' style="margin-bottom: 0px;" name='password'
				type="password" placeholder="Password">
		</div>
	</div>

	<div style="margin-top: 10px" class="row">
		<div class="large-12 large-centered columns">
			<input type="submit" class="button radius small" value="OK">
			<a href="registration" class="button radius small">Register</a>
		</div>
	</div>
</form>
</div>
