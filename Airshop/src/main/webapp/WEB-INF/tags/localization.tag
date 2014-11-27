<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<li class='has-dropdown not-click'><a>Language (${pageContext.request.locale.language})</a>
    <ul class='dropdown'>
        <c:forEach items="${languages}" var="item">
            <li><a href="?lang=${item}">${item}</a>
        </c:forEach>
	</ul>
</li>
<li class='divider'></li>







