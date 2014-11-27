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
            <div class="large-9 columns">
                <span id = "cartIsEmptyMessage"></span>
                <table id = "table" border="1">
                   <caption>Your cart</caption>
                   <tr>
                        <th style="width:5%;">№</th>
                        <th style="width:40%;">Photo</th>
                        <th style="width:25%;">Name</th>
                        <th style="width:15%;">Count</th>
                        <th style="width:15%;">Price</th>
                   </tr>
                   <c:forEach items="${productsInCart.getProducts()}" var="entry" varStatus="loop">
                     <tr id = "${loop.count}">
                         <td>${loop.count}</td>
                         <td><img src="avatar.jpg?photo=${entry.key.photo}"></td>
                         <td>${entry.key.name}</td>
                         <td>
                            <a style = "padding-left: 5px; width = 20px; height = 20px; padding-right: 5px; padding-top: 0px; padding-bottom: 0px;" onclick = "decrementProduct('${entry.key.name}', '${loop.count}_1')" class="button radius small">-</a>
                            <input type = "text" id = "${loop.count}_1" value = "${entry.value}" onblur="setProduct('${entry.key.name}', '${loop.count}_1')"/>
                            <a style = "padding-left: 5px; width = 20px; height = 20px; padding-right: 5px; padding-top: 0px; padding-bottom: 0px;" onclick = "incrementProduct('${entry.key.name}', '${loop.count}_1')" class="button radius small">+</a></td>
                         <td>${entry.key.price}$</td>
                         <td><a onclick = "removeProduct('${loop.count}', '${entry.key.name}')">&#215;</a></td>
                     </tr>
                   </c:forEach>
                   <tr>
                        <td id = "totalRow" colspan="4" align = "right">Total: ${total}</td>
                        <td colspan="2" align = "center">
                            <ul class="button-group">
                              <li><a onclick = "clearCart()" class="button radius small"  style=" padding-right: 5px; padding-left: 5px;">Clear</a></li>
                              <li><a href="buyingpage" class="button radius small" style=" padding-right: 10px; padding-left: 10px;">Buy</a></li>
                            </ul>
                        </td>
                   </tr>
                </table>
            </div>
        <jsp:include page="footer.jsp"/>
    </div>
</div>
<script>
    $(document).foundation();

    function removeProduct(rowIndex, productName){
        $('#'+rowIndex).remove()
        var xmlhttp = new XMLHttpRequest();
                    xmlhttp.open('GET', '/Airshop/removefromcart?product=' + productName, true);
                    xmlhttp.onreadystatechange = function() {
                      if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                       var Data = JSON.parse(xmlhttp.responseText);
                             var productCount = Data.total;
                             $("#totalRow").text('Total: ' + productCount);
                      }
                    };
                    xmlhttp.send(null);
    }

    function clearCart(){

            var xmlhttp = new XMLHttpRequest();
            xmlhttp.open('POST', '/Airshop/removefromcart', false);
            xmlhttp.onreadystatechange = function() {
            $('#table').remove();
             $("#cartIsEmptyMessage").text('Your cart is empty!');
            };
            xmlhttp.send(null);
        }

    function incrementProduct(name, spanId){
                var xmlhttp = new XMLHttpRequest();
                xmlhttp.open('GET', '/Airshop/addtocart?product=' + name, true);
                xmlhttp.onreadystatechange = function() {
                  if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                   var Data = JSON.parse(xmlhttp.responseText);
                         var total = Data.total;
                         $("#totalRow").text('Total: ' + total);
                         var prevCount = parseInt($('#'+spanId).val());
                         prevCount++;
                         $('#'+spanId).val('' + prevCount);
                  }
                };
                xmlhttp.send(null);
            }

    function decrementProduct(name, spanId){
                    var xmlhttp = new XMLHttpRequest();
                    xmlhttp.open('GET', '/Airshop/removefromcart?product=' + name+"&count=1", true);
                    xmlhttp.onreadystatechange = function() {
                      if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                       var Data = JSON.parse(xmlhttp.responseText);
                             var total = Data.total;
                             $("#totalRow").text('Total: ' + total);
                             var prevCount = parseInt($('#'+spanId).val());
                             prevCount--;
                             $('#'+spanId).val('' + prevCount);
                      }
                    };
                    xmlhttp.send(null);
                }

    function setProduct(name, spanId){
                    var xmlhttp = new XMLHttpRequest();
                    xmlhttp.open('GET', '/Airshop/setproduct?product=' + name+"&count="+$('#'+spanId).val(), true);
                    xmlhttp.onreadystatechange = function() {
                      if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                       var Data = JSON.parse(xmlhttp.responseText);
                             var total = Data.total;
                             $("#totalRow").text('Total: ' + total);
                      }
                    };
                    xmlhttp.send(null);
                }

</script>
</body>
</html>