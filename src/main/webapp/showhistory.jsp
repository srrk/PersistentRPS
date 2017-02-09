<%-- 
    Document   : savehistory
    Created on : 9 Feb, 2017, 10:14:19 AM
    Author     : zeroadmin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page errorPage="error.jsp" %>

<%@taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri = "http://java.sun.com/jsp/jstl/sql" prefix="sql" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Game History</title>
    </head>
    <body>
        <h1>Hello ${pageContext.session.getAttribute("username")} !, Following is your saved History.</h1>
        
        <sql:setDataSource
            var = "myDS"
            driver = "org.sqlite.JDBC"
            url = "jdbc:sqlite:/tmp/test.db"
        />
        
        <sql:query
            var = "listHistory" 
            dataSource="${myDS}"
            >
            SELECT userchoice,syschoice,winner,timestamp FROM Rps where username = "${pageContext.session.getAttribute("username")}";
        </sql:query>
            
        <div align="center">
            <table class="products" border = "1" cellpadding = "5">
                <tr>
                    <th>User choice</th>
                    <th>System Choice</th>
                    <th>Winner</th>
                    <th>TimeStamp</th>
                </tr>
                <c:forEach var = "item" items = "${listHistory.rows}">
                    <tr>
                        <td class = "rght"><c:out value="${item.userchoice}" /></td>
                        <td class = "cent"><c:out value="${item.syschoice}" /></td>
                        <td class = "left"><c:out value="${item.winner}" /></td>
                        <td class= "left"><c:out value="${item.timestamp}" /></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
            
        <a href="${pageContext.request.contextPath}/dashboard.jsp">Dashboard (Continue Playing)</a>
        
    </body>
</html>
