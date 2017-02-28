<%-- 
    Document   : WeatherJSP
    Created on : Feb 26, 2017, 1:25:14 PM
    Author     : Mike
--%>

<%@page import="weatherservice.OpenWeatherModel"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Weather Page</title>
    </head>
    <body>
        <h1>Hello!</h1>
        <form>
            <table width="75%">
                <tr> 
                    <td width="48%">What is your zipcode?</td>
                    <td width="52%">
                        <input type="text" name="zip" />
                    </td>
                </tr>
            </table>
            <p> 
                <input type="submit" name="Submit" value="Submit name" />
                <input type="reset" name="Reset" value="Reset form" />
            </p>
            <%
                String zip = request.getParameter("zip");
                if (zip == null) { %>
            <p>Please enter your 5-digit zip code.</p> 
            <% } else {
                OpenWeatherModel model = new OpenWeatherModel(zip);
                if (model.getValid()) {
            %>
            <p>Location: <%= model.getName()%></p>
            <p>Temp: <%= model.getTemp()%> K </p>
            <p>Humidity: <%= model.getHumidity()%> % </p>
            <p>Wind Speed: <%= model.getWindSpeed()%> m/s </p>
            <p>Cloudiness: <%= model.getOvercast()%> % </p>
            <p>Number of searches for this area by you: <%= model.incSearchCount(zip) %></p>
            <% } else { %>
            <p>Please enter a valid 5-digit zip code.</p>
            <%   }
                }
            %>
        </form>
    </body>
</html>
