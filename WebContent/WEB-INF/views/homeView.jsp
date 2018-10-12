<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css">
<title>Insert title here</title>
</head>
<body>

	<jsp:include page="_header.jsp"></jsp:include>
    
      	<h3>Home Page</h3>
      
      	This is a simple demo of a web application using jsp's,servlets, javascript &amp; Jdbc. <br><br>
      	<b>It includes the following functions:</b>
      	<ul>
         <li>Login</li>
         <li>Storing user information in cookies</li>
         <li>GIF API</li>
      	</ul>
 
    <jsp:include page="_footer.jsp"></jsp:include>

</body>
</html>