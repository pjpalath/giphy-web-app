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
 
    <h3>Edit Product</h3>
 
    <p style="color: red;">${errorString}</p>
 
    <c:if test="${not empty animatedgif}">
       <form method="POST" action="${pageContext.request.contextPath}/editGif">
          <input type="hidden" name="url" value="${animatedgif.url}" />
          <table border="0">
             <tr>
                <td>GIF Url</td>
                <td style="color:red;">${animatedgif.url}</td>
             </tr>
             <tr>
                <td>GIF Type</td>
                <td><input type="text" name="type" value="${animatedgif.type}" /></td>
             </tr>             
             <tr>
                <td colspan = "2">
                    <input type="submit" value="Submit" />
                    <a href="${pageContext.request.contextPath}/gifList">Cancel</a>
                </td>
             </tr>
          </table>
       </form>
    </c:if>
 
    <jsp:include page="_footer.jsp"></jsp:include>

</body>
</html>