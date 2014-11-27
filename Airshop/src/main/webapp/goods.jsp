<%@ page language="java" contentType="text/html; charset= UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ page session="true" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
    <%@ taglib tagdir="/WEB-INF/tags" prefix="my_tags" %>
    <meta http-equiv="Content-Type" content="text/html; charset= UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Air Shop</title>
    <jsp:include page="includes.jsp"/>
</head>
<body>
<div class="row">
    <div class="large-12 columns">
        <jsp:include page="header.jsp"/>
        <div id="firstModal" class="reveal-modal small" data-reveal>
            <img id="modalImg" src="">

            <h3 align="center" id="modalProductName"></h3>
            <h4 align="center" id="modalProductPrice"></h4>
            <input type='hidden' id = "value" name='product' value=''>
            <my_tags:add_to_cart_button/>
            <a class="close-reveal-modal">&#215;</a>
        </div>
        <div class="row">
            <div style="margin-top: 50px" class="large-3 columns">
                <div class="row">
                    <my_tags:login/>
                </div>
                <div class="row">
                    <my_tags:filterpanel/>
                </div>
                <div class="row">
                    <div style="padding-bottom: 0px; margin-left: 15px" class="panel">
                        <div class="row">
                             Goods in cart:<span id = "productSpan"> <c:out value="${sessionScope.goodsInCart}"/></span>
                            <a href="cart" class="button radius tiny">Buy all</a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="large-9 columns">
                <div class="row">
                    <c:choose>
                        <c:when test="${fn:length(products) == 0}">
                          <div align="center"> Any products were found! </div>
                        </c:when>
                        <c:when test="${fn:length(products) > 0}">
                            <c:forEach items="${products}" var="product" varStatus="loop">
                                <c:if test="${loop.count % 4 == 0}">
                                     </div>
                                     <div class="row">
                                </c:if>
                                 <div onclick="showGoodsModal('avatar.jpg?photo=${product.photo}', '${product.name}', 'Price: ${product.price}$' );"
                                    name="productThumbnail" class="large-4 columns">
                                    <a data-reveal-id="firstModal" class="th radius">
                                    <img src="avatar.jpg?photo=${product.photo}">
                                    <h3 align="center">${product.name}</h3>
                                    <h4 align="center">Price: ${product.price}$</h4>
                                    </a>
                                 </div>
                             </c:forEach>
                        </c:when>
                    </c:choose>
                </div>
                     <my_tags:pagination/>
             </div>
        </div>
        <jsp:include page="footer.jsp"/>
    </div>
</div>
<script>
	$(document).foundation();
</script>
</body>
</html>