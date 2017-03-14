<%-- 
    Document   : CheckUser
    Created on : Mar 1, 2017, 3:47:55 PM
    Author     : thom
--%>

<%@page import="weatherservice.DBManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>CheckUser</title>
    </head>
    <body>
        <% 
            DBManager DB = new DBManager();
            String email = request.getParameter("email"); 
            String password =request.getParameter("password");
            String username = request.getParameter("username"); 
            
            if(username == null){
              
                if(DB.findUser(password, email)){ 
                    session.setAttribute("username", email); 
                    response.sendRedirect("homepage.jsp"); 
                } 
                else{
                    session.setAttribute("found", false);
                    response.sendRedirect("index.jsp"); 
                }
            }
            else{
                session.setAttribute("username", username); 
                DB.addUser(username, password, email);
                response.sendRedirect("homepage.jsp"); 
            }

        %>


    </body>
</html>
