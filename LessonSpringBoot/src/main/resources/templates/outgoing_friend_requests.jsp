<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <!-- Header -->
    <jsp:include page= "header.jsp" />
    <!-- Кодировка веб-страницы -->
    <meta charset="utf-8">
    <!-- Настройка viewport -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>...</title>

    <!-- Bootstrap CSS (jsDelivr CDN) -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <!-- Bootstrap Bundle JS (jsDelivr CDN) -->
    <script defer src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
            crossorigin="anonymous"></script>
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
        .button {
            background-color: #4CAF50;
            border: none;
            color: white;
            padding: 10px 15px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            margin: 4px 2px;
            cursor: pointer;
        }
    </style>
</head>
<body onload="active()">

<div class="container-fluid text-center wrapper">
    <br class="row content">
    <div class="col-sm-8 text-left mainContent">
        <h1>Outgoing friend requests</h1>
    </div>
    <div class="container p-30">
        <div class="row">
            <div class="col-md-12 main-datatable">
                <div class="card_body">
                </div>
                <div class="overflow-x">
                    <table style="width:100%;"
                           id="filtertable"
                           class="table cust-datatable dataTable no-footer"
                           aria-describedby="users">
                        <thead>
                        <tr>
                            <th style="min-width:50px;">User id</th>
                            <th style="min-width:150px;">Name</th>
                            <th style="min-width:150px;">Status</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${outgoingFriendRequest}" var="user">
                            <tr>
                                <td>
                                    <span class="text-primary"><c:out value="${user.userId}" /></span>
                                </td>
                                <td>
                                    <c:out value="${user.userName}"/>
                                </td>
                                <td>
                                    <form action="outgoing_friend_requests" method="post">
                                        <input type="hidden" name="cancelUserID" value="${user.userId}"/>
                                        <button type="submit" class="button">Отменить</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
</body>
</html>
