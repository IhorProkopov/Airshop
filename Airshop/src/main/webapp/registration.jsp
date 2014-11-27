<%@ page language="java" contentType="text/html; charset= UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="cap" uri="http://www.shop.com/captcha"%>
<fmt:setLocale value="${pageContext.request.locale}"/>
<fmt:setBundle basename="bundle.messages"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Registration</title>
<jsp:include page="includes.jsp" />
</head>
<body>
<fmt:message key="registration.name" var="name_holder" />
<fmt:message key="registration.surname" var="surname_holder" />
<fmt:message key="registration.email" var="email_holder" />
<fmt:message key="registration.password" var="pass_holder" />
<fmt:message key="registration.passwordConfirmation" var="confPass_holder" />
<fmt:message key="registration.receiveNewsletter" var="newsletters_holder" />
<fmt:message key="registration.captcha" var="captcha_holder" />
<fmt:message key="registration.registration" var="registration_holder" />
	<div class="row">
		<div class="large-12 columns">
			<jsp:include page="header.jsp" />

			<form action="registration" class="registration"
				onsubmit="return Registration();" method="POST"
				enctype="multipart/form-data">
				<div class="row">
					<div align="center" class="large-12 columns">
						<h2>${registration_holder}</h2>
						<c:forEach items="${validation}" var="item">
							<small id='userExistError' class='error' style='margin-top: 0px;'>${item}</small>
						</c:forEach>
					</div>
				</div>
				<div class="row">
					<div style="margin-top: 15px"
						class="large-12 large-centered columns">	
						<input style="margin-bottom: 0px;" type="file" name="photo" /> 					
					</div>
				</div>
				<div class="row">
					<div style="margin-top: 15px"
						class="large-12 large-centered columns">
						<input style="margin-bottom: 0px;" id='Name' name='name'
							type="text" placeholder="${name_holder}" value="${name}"> <small
							id='nameerror' class="error"
							style="display: none; margin-top: 0px">Invalid name!</small>
					</div>
				</div>

				<div class="row">
					<div style="margin-top: 15px"
						class="large-12 large-centered columns">
						<input id='Surname' style="margin-bottom: 0px;" value="${surname}"
							name='surname' type="text" placeholder="${surname_holder}" /> <small
							id='surnameerror' class="error"
							style="display: none; margin-top: 0px">Invalid surname</small>
					</div>
				</div>

				<div class="row">
					<div style="margin-top: 15px"
						class="large-12 large-centered columns">
						<input id='Email' style="margin-bottom: 0px;" value="${email}"
							name='email' type="text" placeholder="${email_holder}" onkeyup="checkUser()"> <small
							id='emailerror' class="error"
							style="display: none; margin-top: 0px">Invalid email!</small>
							<small id='userExistError' class='error'
                            							style='display: none; margin-top: 0px;'>This
                            							user already exist!</small>
					</div>
				</div>

				<div class="row">
					<div style="margin-top: 15px"
						class="large-12 large-centered columns">
						<input id='Password' style="margin-bottom: 0px;" name='password'
							type="password" placeholder="${pass_holder}"> <small
							id='passworderror' class="error"
							style="display: none; margin-top: 0px">Invalid password!</small>
						<small id='equalerror' class="error"
							style="display: none; margin-top: 0px">Passwords are
							different!</small>
					</div>
				</div>
				<div class="row">
					<div style="margin-top: 15px"
						class="large-12 large-centered columns">
						<input id='confirmPassword' style="margin-bottom: 0px;"
							name='confPassword' type="password"
							placeholder="${confPass_holder}"> <small
							id='confirmpassworderror' class="error"
							style="display: none; margin-top: 0px">Invalid password!</small>
					</div>
				</div>
				<div class="row">
					<div style="margin-top: 15px"
						class="large-12 large-centered columns">
						<label for="checkbox"><input id="checkbox" value='true'
							name="delivery" type="checkbox">${newsletters_holder}</label>
					</div>
				</div>
				<div class="row">
					<div
						style="margin-top: 15px; margin-right: 5px; padding-right: 0px;"
						class="large-4 large columns">
						<cap:captcha id="${captca_id }" hidden="${hidden_field}" />
					</div>
					<div style="margin-top: 15px; margin-left: 0px; padding-left: 0px;"
						class="large-7 large columns">
						<input style="margin-bottom: 0px;" id='Captcha' name='captcha'
							type="text" placeholder="${captcha_holder}"> <small
							id='captError' class='error'
							style="display: none; margin-top: 0px">Insert number from
							image!</small>
					</div>
				</div>

				<div class="row">
					<div class="large-3 large-centered columns">
						<input type="submit" class="button radius" value="OK">
					</div>
				</div>
			</form>
		</div>
	</div>
	<script src="./js/validation.js"></script>
	<script>
        $(document).foundation();

    function checkUser(){
       var xmlhttp = new XMLHttpRequest();
       xmlhttp.open('GET', '/Airshop/checkuser?email=' +document.getElementById('Email').value, true);
       xmlhttp.onreadystatechange = function() {
         if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
          var Data = JSON.parse(xmlhttp.responseText);
                var exist = Data.exist;
                if(exist){
                    show(document.getElementById('userExistError'));
                }else{
                    hide(document.getElementById('userExistError'));
                }
         }
       };
       xmlhttp.send(null);
    }

    </script>
</body>
</html>