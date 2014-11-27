<%@ tag pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:choose>
	<c:when test="${ not empty user}">
		<jsp:include page="user.jsp" />
	</c:when>
	<c:when test="${empty user}">
		<jsp:include page='login.jsp' />
	</c:when>	
</c:choose>
