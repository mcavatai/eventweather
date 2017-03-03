<%-- 
    Document   : newAccount
    Created on : Feb 28, 2017, 4:14:10 PM
    Author     : thom
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sign up!</title>
    </head>
    <body>
        <center>
            <b><font size="4"> CREATE NEW ACCOUNT</font></b><br/>&nbsp;
            <form name ="Signup" method="post" action="CheckUser.jsp">
                Enter Email: <br />
                <input type="text" name ="email"> <br />
                Enter Username: <br />
                <input type="text" name ="username"> <br />
                Enter Password: <br />
                <input type="password" name ="password"> <br />
                <input type="submit" value="Enter">
            </form>
        </center>
    </body>
</html>
