<%-- 
    Document   : History
    Created on : Oct 7, 2016, 9:16:44 PM
    Author     : josepharcelo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Account History</title>
    </head>
    <body>
        <h3>Credit card log for account: ${card.accountId}</h2>
            <ul class="list-group">
                <c:forEach var="s" items="${card.creditHistory}">
                        <li class="list-group-item">${s}</li>
                </c:forEach>
            </ul>
    </body>
</html>
