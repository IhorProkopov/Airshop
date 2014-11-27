<%@ tag pageEncoding="UTF-8"%>
<%@ taglib prefix="cc" uri="http://www.shop.com/categoriescheckbox"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div style="padding-bottom: 0px; margin-left: 15px" class="panel">
    <div class = "row" align = "center">
            <a href="#" data-dropdown="sortdrop">Sort</a>
            <a> | </a>
            <a href="#" data-dropdown="productsonpage">Products on page</a>
            <ul id="sortdrop" class="f-dropdown" data-dropdown-content>
                <li><a href="sort?sort=price">By price</a></li>
                <li><a href="sort?sort=pricereverse">By price reverse</a></li>
                <li><a href="sort?sort=name">By name</a></li>
                <li><a href="sort?sort=namereverse">By name reverse</a></li>
            </ul>
            <ul id="productsonpage" class="f-dropdown" data-dropdown-content>
                <li><a href="sort?productsonpage=3">3</a></li>
                <li><a href="sort?productsonpage=6">6</a></li>
                <li><a href="sort?productsonpage=9">9</a></li>
                <li><a href="sort?productsonpage=12">12</a></li>
            </ul>
    </div>
    <form action="filter" method="POST">
        <div class = "row">
            <c:forEach items="${filterErrors}" var="item">
                <small class='error' style='margin-top: 0px;'>${item}</small>
            </c:forEach>
        </div>
        <div class="row">
            <div style="margin-top: 15px" class="large-12 large-centered columns">
                <input id='country' style="margin-bottom: 0px;" value = "${country_holder}"
                       name='country' type="text" placeholder="Country">
            </div>
        </div>
        <div class="row">
            <div style="margin-top: 15px" class="large-12 large-centered columns">
                <input id='manufacturer' style="margin-bottom: 0px;" value = "${manufacturer_holder}"
                       name='manufacturer' type="text" placeholder="Manufacturer">
            </div>
        </div>
        <div class="row">
            <div style="margin-top: 15px" class="large-12 large-centered columns">
                <input id='name' style="margin-bottom: 0px;" value = "${name_holder}"
                       name='name' type="text" placeholder="Name">
            </div>
        </div>
        <div class="row">
            <div style="margin-top: 15px" class="large-12 large-centered columns">
                <input id='minPrice' style="margin-bottom: 0px;" value = "${minPrice_holder}"
                       name='minPrice' type="text" placeholder="Minimum price">
            </div>
        </div>
        <div class="row">
            <div style="margin-top: 15px" class="large-12 large-centered columns">
                <input id='maxPrice' style="margin-bottom: 0px;" value = "${maxPrice_holder}"
                       name='maxPrice' type="text" placeholder="Maximum price">
            </div>
        </div>
        <div class="row">
            <div style="margin-top: 15px"
                 class="large-12 large-centered columns">
                <label for="checkbox"><input id="checkbox" value='true' "
                                             name="photo" type="checkbox" ${photo_holder}>Do you need
                    photo?</label>
            </div>
        </div>
        <c:forEach items="${categories}" var="category" varStatus="loop">
                 <div class = "row">
                    <div style="margin-top: 15px"
                    class="large-12 large-centered columns">
                    <cc:categoriescheckbox category="${category}" categories="${checkedCategories}" />
                    </div>
                 </div>
        </c:forEach>
        <div style="margin-top: 10px" class="row">
            <div class="large-11 large-centered columns">
                <input type="submit" class="button radius tiny" value="OK">
                <a href="resetsort" class="button radius tiny">Reset filters</a>
            </div>
        </div>
     </form>
</div>