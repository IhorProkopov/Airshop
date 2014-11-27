<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="loc" %>
<nav class='top-bar' data-topbar>
	<ul class='title-area'>
		<li class='name'>
			<h1>
				<a href='goods'>Air Shop</a>
			</h1>
		</li>
	</ul>
	<section class='top-bar-section'>
		<ul class='right'>
		    <loc:localization />
			<li class='has-dropdown not-click'><a href='goods'>Goods</a>
				<ul class='dropdown'>
					<li><a href='#'>Fighters</a></li>
					<li><a href='#'>Bombers</a></li>
					<li><a href='#'>Airliners</a></li>
				</ul>
			</li>
			<li class='divider'></li>
			<li><a href='#'>My cart</a></li>
		</ul>
	</section>
</nav>