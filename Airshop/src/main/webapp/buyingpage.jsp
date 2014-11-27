<%@ page language="java" contentType="text/html; charset= UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ page session="true" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib tagdir="/WEB-INF/tags" prefix="log_form" %>
    <meta http-equiv="Content-Type" content="text/html; charset= UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Air Shop</title>
    <jsp:include page="includes.jsp"/>
</head>
<body>
    <div class="row">
        <div class="large-12 columns">
            <jsp:include page="header.jsp"/>
        </div>
        <div class="row">
            <div style="margin-top: 50px" class="large-3 columns">
                <div class="row">
                    <log_form:login/>
                </div>
            </div>
            <div class="large-4 columns">
                <form action="buyingpage" method="POST"  style="margin-top: 40px;">
                	<select name = "payment" id="customDropdown1" class="medium" required="">
                        <option value="">Select Payment type</option>
                        <c:forEach items="${paymentTypes}" var="payment" varStatus="loop">
                            <option value="${payment}">${payment}</option>
                        </c:forEach>
                    </select>
                    </label>

                    <div class="row">
                    	<div style="margin-top: 15px" class="large-12 large-centered columns">
                    		<input id='delivery' style="margin-bottom: 0px;"
                    			name='delivery' type="text" placeholder="Delivery type" required>
                    	</div>
                    </div>

                    <div class="row">
                    	<div style="margin-top: 15px" class="large-12 large-centered columns">
                    		<input id='props' style="margin-bottom: 0px;"
                    			name='props' type="text" placeholder="Props" required>
                    	</div>
                    </div>

                    <div class="row">
                    	<div style="margin-top: 15px" class="large-12 large-centered columns">
                    		<input id='comments' style="margin-bottom: 0px;"
                    			name='comments' type="text" placeholder="Comments">
                    	</div>
                    </div>

                	<div style="margin-top: 10px" class="row">
                		<div class="large-12 large-centered columns">
                			<input type="submit" class="button radius small" value="OK">
                		</div>
                	</div>
                </form>


            </div>
            <jsp:include page="footer.jsp"/>
        </div>
    </div>
    <script>
        $(document).foundation();
    </script>
</body>
</html>