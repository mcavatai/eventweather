<%-- 
    Document   : logout
    Created on : Mar 20, 2017, 6:50:14 PM
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
        <%
            session.invalidate();
            response.sendRedirect(request.getContextPath());
        %>
    </body>
</html>
