/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weatherservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet responsible for displaying weather data based on a requested ZIP
 * code.
 */
public class WeatherInfo extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        String paramName = "zipcode";
        String paramValue = request.getParameter(paramName);

        if (paramValue != null && paramValue.length() == 5) {

            Integer searchCount = (Integer) session.getAttribute(paramValue + ".count");
            if (searchCount == null) {
                searchCount = new Integer(1);
            } else {
                searchCount++;
            }
            session.setAttribute(paramValue + ".count", searchCount);
            
            URL openweather = new URL("http://api.openweathermap.org/data/2.5/weather?zip=" + paramValue + ",us&APPID=b4cdb3d940b23503251ce4883be8c49f");
            BufferedReader in = new BufferedReader(new InputStreamReader(openweather.openStream()));

            String jsonString = "";
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                jsonString += inputLine;
            }
            in.close();

            JSONObject json;
            JSONObject main;
            JSONObject wind;
            JSONObject clouds;
            
            String name = "";
            String temp = "";
            String humid = "";
            String wSpeed = "";
            String overcast = "";

            try {
                json = new JSONObject(jsonString);
                name = (String) json.get("name");
                main = (JSONObject) json.get("main");
                wind = (JSONObject) json.get("wind");
                clouds = (JSONObject) json.get("clouds");
                temp = (String) main.get("temp").toString();
                humid = (String) main.get("humidity").toString();
                wSpeed = (String) wind.get("speed").toString();
                overcast = (String) clouds.get("all").toString();
            } catch (JSONException ex) {
                System.out.println(ex.toString());
            }

            out.println("<!DOCTYPE html>");
            out.println("<html><head>");
            out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
            out.println("<title>Weather Data</title></head>");
            out.println("<body>");
            out.println("<h1>Requested data for your location...</h1>");
            out.println("<p>Your Location: <stroong>" + name + "</strong></p>");
            out.println("<p>Current Temperature: <strong>" + temp + " K</strong></p>");
            out.println("<p>Current Humidity: <strong>" + humid + "%</strong></p>");
            out.println("<p>Current Wind Speed: <strong>" + wSpeed + " m/s</strong></p>");
            out.println("<p>Cloudiness: <strong>" + overcast + "%</strong></p>");
            out.println("<p>JSON item: \n" + jsonString + "</p>");
            out.println("</body>");
            out.println("</html>");
            out.close();

        } else {
            out.println("<!DOCTYPE html>");
            out.println("<html><head>");
            out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
            out.println("<title>Weather Data</title></head>");
            out.println("<body>");
            out.println("<h1>No valid zip code given. Please search by adding \"?zipcode=\" to the URL, followed by a valid US zip code.</h1>");
            out.println("</body>");
            out.println("</html>");
            out.close();
        }
    }
}
