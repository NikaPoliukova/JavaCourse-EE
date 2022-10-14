
<%@ page import="java.util.List" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="example.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Hello, <%=session.getAttribute("username")%></h1>
<h1>Users</h1>
<table>
    <tr>
        <th>Name</th>
    </tr>
    <tbody>
        <% for (User user : (List<User>) request.getAttribute("users")) {%>
    <tr>
        <td>
            <%=user.getUserName()%>
        </td>
    </tr>
    <%} %>
    </tbody>
</table>
</body>
</html>