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
 
    <h3>Hello: ${user.userName}</h3>
 
    User Name: <b>${user.userName}</b>
    <br />
 
    <jsp:include page="_footer.jsp"></jsp:include>

</body>
</html>