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
<%@ page import="Eventful.Eventful" %>
<%@ page import="com.evdb.javaapi.EVDBRuntimeException" %>
<%@ page import="com.evdb.javaapi.EVDBAPIException" %>
<%@ page import="com.evdb.javaapi.data.Event" %>
<%@ page import="com.evdb.javaapi.data.Image" %>


<html>
<head>
    <title>Events</title>
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
    <a class='clickEvent' onclick='eventClicked()'>
    <li>

        <%
            Image img = event.getImages().get(0);
            out.println("Event Name: "+event.getTitle());
            %>

        <br>
        <%
            out.println("Venue: "+event.getVenueAddress());
            %>
        <br>
        <%
            out.println("ZIPCODE: "+event.getVenuePostalCode());
            %>
        <br>
        <%
            out.println("Time: "+event.getStartTime() + " - "+event.getStopTime());
        %>
        <br>
        <a href="TestJSP.jsp?zip=<%= event.getVenueCity() %>" class="button">Go to Event Page</a>
        <%--<img src="<%= img.getUrl() %>">--%>
    </li>
    </a>

    <br>
    <%
        }
        }catch (EVDBAPIException e1){
            System.out.println("loading...");
        }
    %>
</ol>
<style type="text/css">
    a.clickEvent:hover {
        cursor: pointer;
        color:rebeccapurple;
    }
</style>
</body>
</html>
