<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link href="${pageContext.request.contextPath}/css/header.css" rel="stylesheet" type="text/css">

<div class="container">
	<div class="header">
	  <a href="#default" class="logo">GIPHY Wrapper Web Page</a>
	  <div class="header-right">    
	    <a href="#contact">Contact</a>
	    <a href="#about">About</a>
	    
	    <c:if test="${not empty loginedUser.userName}">
			<div style="float: right; padding: 10px; text-align: right;">
				<!-- User store in session with attribute: loginedUser -->
				Hello <b>${loginedUser.userName}</b>
				<br/>
				<a href="logout" >Logout</a>
			</div>
	  	</c:if>    
	  </div>  
	</div>
	<jsp:include page="_menu.jsp"></jsp:include>
</div>