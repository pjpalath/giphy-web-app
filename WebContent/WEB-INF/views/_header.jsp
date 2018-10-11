<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div style="background: #E0E0E0; height: 55px; padding: 5px;">
  <div style="float: left">
     <h1>My Site</h1>
  </div>
 
  <c:if test="${not empty loginedUser.userName}">
	<div style="float: right; padding: 10px; text-align: right;">
		<!-- User store in session with attribute: loginedUser -->
		Hello <b>${loginedUser.userName}</b>
		<br/>
		<a href="logout" >Logout</a>
	</div>
  </c:if>
 
</div>