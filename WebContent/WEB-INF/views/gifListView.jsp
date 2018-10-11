<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<jsp:include page="_header.jsp"></jsp:include>
    <jsp:include page="_menu.jsp"></jsp:include>
 
    <h3>GIF in Account Profile</h3>
 
    <p style="color: red;">${errorString}</p>
 
    <table border="1" cellpadding="5" cellspacing="1" >
       <tr>
          <th>GIF Url</th>
          <th>GIF Type</th>          
          <th>Delete</th>
       </tr>
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
    </table>
 
    <a href="addGif" >Create Gif</a>
 
    <jsp:include page="_footer.jsp"></jsp:include>

</body>
</html>