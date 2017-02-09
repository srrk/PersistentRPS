<%-- 
    Document   : error
    Created on : 2 Feb, 2017, 12:38:29 PM
    Author     : zeroadmin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page isErrorPage="true" %>
<%@page import="java.io.*" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error page</title>
    </head>
    <body>
        Message : <%= exception.getMessage() %>
        <br>
        <br>
    <bold>StackTrace : </bold> 
            <%
                StringWriter stringwriter = new StringWriter();
                PrintWriter printwriter = new PrintWriter(stringwriter);
                exception.printStackTrace(printwriter);
                out.print(stringwriter);
                printwriter.close();
                stringwriter.close();
            %>
            
    </body>
</html>
