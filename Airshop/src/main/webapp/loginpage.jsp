<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cap" uri="http://www.shop.com/captcha"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Login</title>
<jsp:include page="includes.jsp" />
</head>
<body>
	<div class="row">
		<div class="large-12 columns">
			<jsp:include page="header.jsp" />
			<div style="margin-right: 15px">
				<jsp:include page='login.jsp' />
			</div>
		</div>
	</div>
	<script>
    	$(document).foundation();
    </script>
</body>
</html>