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
        <h1>Credit card log for account: </h1>
        ${card.accountId}
        <br>
        <table>
            <c:forEach var="s" items="${card.creditHistory}">
                <tr>
                    <td>${s}</td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
