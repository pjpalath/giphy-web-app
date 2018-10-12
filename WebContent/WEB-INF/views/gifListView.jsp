<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css">
<title></title>
</head>
<body>

	<jsp:include page="_header.jsp"></jsp:include>    
 
 	<h1 class="title">Giphy API Search</h1>
 
 	<div class="input-wrapper empty">
    	<input type="text" class="query" />
    	<div class="loader"></div>
    	<div class="clear"></div>
	</div>

	<div id="result" class="result">
	</div>

	<div class="random-outer">
  		<button id="getAnotherGifButton" class="random">Get Another GIF</button>
  		<!--<form action="addGif" method="post" class="random" id="addGifForm">-->
  		<form action="addGif" method="post" class="random" id="addGifForm">
  			<input type="hidden" value='url' name="gifurl" id="gifurl" />
  			Select a Category:&nbsp;
    		<select name="category">
        		<c:forEach items="${listCategory}" var="category">
            		<option value="${category}">${category}</option>
        		</c:forEach>
    		</select>
    		<br/><br/>
  		</form>
  		<button id="saveGifButton" class="random">Save GIF to Profile</button>
	</div>    
 
    <p style="color: red;">${errorString}</p>
 
    <table id="gifTable">
       <thead>
	       <tr>
	          <th>GIF Url</th>
	          <th>GIF Type</th>         
	          <th>Edit</th>
	          <th>Delete</th>
	       </tr>
       </thead>
       <tbody>
	       <c:forEach items="${gifList}" var="gif" >
	          <tr>
	             <td>${gif.url}</td>
	             <td>${gif.type}</td>             
	             <td>
	                <a href="editGif?url=${gif.url}">Edit</a>
	             </td>
	             <td>
	                <a href="deleteGif?url=${gif.url}">Delete</a>
	             </td>
	          </tr>
	       </c:forEach>
       </tbody>
    </table>    
 
    <jsp:include page="_footer.jsp"></jsp:include>

</body>
</html>