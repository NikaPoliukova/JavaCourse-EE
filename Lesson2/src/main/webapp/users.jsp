<%@ page import="java.util.List" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="example.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        table {
            border-collapse: collapse;
            border-spacing: 0;
            width: 100%;
            border: 1px solid #ddd;
        }

        th, td {
            text-align: left;
            padding: 16px;
        }

        tr:nth-child(even) {
            background-color: #f2f2f2
        }
    </style>
</head>
<body>

<h2>Users</h2>
<p>List of all users</p>

<table>
    <tr>
        <th>User Name</th>
    </tr>
    <tr>
        <tbody>
        <ul>
            <table>
                <c:forEach var="user" items="${users}">
                    <br><c:out value="${user}"/>
                </c:forEach>
            </table>
        </ul>
        </tbody>
    </tr>
</table>
</body>
</html>



