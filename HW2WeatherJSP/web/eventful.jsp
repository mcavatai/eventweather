<%--
  Created by IntelliJ IDEA.
  User: paulk4ever
  Date: 3/1/17
  Time: 5:50 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="classes.Eventful" %>
<%@ page import="com.evdb.javaapi.EVDBRuntimeException" %>
<%@ page import="com.evdb.javaapi.EVDBAPIException" %>
<%@ page import="com.evdb.javaapi.data.Event" %>


<html>
<head>
    <title>Title</title>
</head>
<body>


<form action="index.jsp" method="post">
    Enter Your Search Keyword: &nbsp;
    <input type="text" name="Text">
    <br><br>
    <input type="submit" value="Submit">
</form>
<br>
<h3>Events For : &nbsp;
    <%= request.getParameter("Text")%>
</h3>
<ol>

    <%
        Eventful eventful = new Eventful();
        String kWord = request.getParameter("Text");
        try{
        for (Event event : eventful.search(kWord)) {
    %>
    <li>
        <%
            out.println(event.getTitle());
        %>
    </li>
    <br>
    <%
        }
        }catch (EVDBAPIException e1){
            System.out.println("loading...");
        }
    %>
</ol>
</body>
</html>
