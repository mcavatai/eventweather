<%-- 
    Document   : homepage
    Created on : Feb 28, 2017, 4:29:21 PM
    Author     : thom
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        Welcome 
        <% out.print(session.getAttribute("username")); %>
    </body>
</html>
