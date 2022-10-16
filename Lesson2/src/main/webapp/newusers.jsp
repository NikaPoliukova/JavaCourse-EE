<%@ page import="java.util.List" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="example.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Application</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" />
</head>
<body>
<h1>Hello, <%=session.getAttribute("username")%>
</h1>
<h1>Users</h1>
<table>
    <tbody>
    <ul>
        <c:forEach var="user" items="${users}">
            <li><c:out value="${user}"/></li>
        </c:forEach>
    </ul>
    </tbody>
</table>
</body>
</html>