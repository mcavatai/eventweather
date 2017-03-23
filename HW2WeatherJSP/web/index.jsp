<%-- 
    Document   : index
    Created on : Feb 25, 2017, 9:00:04 PM
    Author     : thom
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>EventfulWeather</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
    <center>
        <b><font size="5"> EVENTFUL WEATHER</font></b><br/>&nbsp;
        Sign in:
        <% 
        if(session.getAttribute("notfound") != null){
            
            out.print("Incorrect username or password ");
            
        }
        %>
            

        <form name ="Signin" method="post" action="CheckUser">
            Enter Email: <br />
            <input type="text" name ="email"> <br />
            Enter Password: <br />
            <input type="password" name ="password"> <br />&nbsp;
            <input type="submit" value="Enter">
        </form>&nbsp;
        
        <form name ="newAccount" method="post" action="newAccount.jsp">
            Don't have an account? Make one! - 
            <input type="submit" value="Sign up"><br />
        </form>
        
        <a href="diagram.html" class="button">Diagram of Web Service</a>
    </center>
    </body>
</html>

