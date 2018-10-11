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
       
    <h3>Add GIF</h3>
       
    <p style="color: red;">${errorString}</p>
       
    <form method="POST" action="${pageContext.request.contextPath}/addGif">
       <table border="0">
          <tr>
             <td>GIF Url</td>
             <td><input type="text" name="url" value="${animatedgif.url}" /></td>
          </tr>
          <tr>
             <td>Type</td>
             <td><input type="text" name="type" value="${animatedgif.type}" /></td>
          </tr>          
          <tr>
             <td colspan="2">                   
                 <input type="submit" value="Submit" />
                 <a href="gifList">Cancel</a>
             </td>
          </tr>
       </table>
    </form>
       
    <jsp:include page="_footer.jsp"></jsp:include>

</body>
</html>