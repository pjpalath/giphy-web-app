<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<link href="${pageContext.request.contextPath}/css/menu.css" rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath}/js/jquery-3.3.1.js"></script>
<script src="${pageContext.request.contextPath}/js/datatables.js"></script>
<script src="${pageContext.request.contextPath}/js/searchGiphy.js"></script>

<nav class="menu">
	<ul class="active">
		<li class="current-item"><a href="${pageContext.request.contextPath}/">Home</a></li>		
		<li><a class="active" href="${pageContext.request.contextPath}/gifList">Fun With GIF's</a></li>
		<li><a href="${pageContext.request.contextPath}/userInfo">My Account Info</a></li>		
	</ul>

	<a class="toggle-nav" href="#">&#9776;</a>

	<form class="search-form">
		<input type="text">
		<button>Search</button>
	</form>
</nav>