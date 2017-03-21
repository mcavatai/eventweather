/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weatherservice.restful;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONException;
import org.json.JSONObject;
import weatherservice.OpenWeatherModel;

/**
 * THIS IS THE CONTROLLER IDIOT.
 *
 * @author Mike
 */
public class RestfulWeather extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Do stuff
//        response.setContentType("text/html;charset=UTF-8");
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

//        String acctEmail = (String) session.getAttribute("email");
//        String zipValue = request.getParameter("zipcode");
        //ask for /restful/users/(email address)/searches/(zipcode)
        //get JSON representing row from Search data table
//        OpenWeatherModel model = new OpenWeatherModel(zipValue);
//        out.println("<!DOCTYPE html>");
//        out.println("<html>");
//        out.println("<head>");
//        out.println("</head>");
//        out.println("<body>");
//        out.println("<p>" + model.getJson() + "</p>");//JSON for weather
//        out.println("<p>" + request.getPathInfo() + "</p>");
//        out.println("</body>");
//        out.println("</html>");

        //This is the weather controller. Grab the zip code and spit out the information given back from the model
        if (request.getPathInfo() != null) {

            String[] uriParameters = request.getPathInfo().substring(1).split("/");
            OpenWeatherModel model = new OpenWeatherModel(uriParameters[0]);
            if (model.getValid()) {
                try {
                    //build a JSON object here.
                    JSONObject feedback = new JSONObject();
                    feedback.append("location", model.getName());
                    feedback.append("temp", model.getTemp());
                    feedback.append("humidity", model.getHumidity());
                    feedback.append("wSpeed", model.getWindSpeed());
                    feedback.append("overcast", model.getOvercast());
                    response.getWriter().write(feedback.toString());
                } catch (JSONException ex) {
                    Logger.getLogger(RestfulWeather.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
