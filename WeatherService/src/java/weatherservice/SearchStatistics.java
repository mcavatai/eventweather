/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weatherservice;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet responsible for checking how many times you looked something up.
 */
public class SearchStatistics extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        String paramName = "zipcode";
        String paramValue = request.getParameter(paramName);

        Integer searchCount = (Integer) session.getAttribute(paramValue + ".count");
        
        if (paramValue != null && paramValue.equals("clear")) {
            session.invalidate();
            out.println("<!DOCTYPE html>");
            out.println("<html><head>");
            out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
            out.println("<title>Weather Data</title></head>");
            out.println("<body>");
            out.println("<p>Cleared search history count.</p>");
            out.println("</body>");
            out.println("</html>");
            out.close();
        } else if (searchCount != null && paramValue != null && paramValue.length() == 5) {
            out.println("<!DOCTYPE html>");
            out.println("<html><head>");
            out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
            out.println("<title>Weather Data</title></head>");
            out.println("<body>");
            out.println("<p>Number of times you searched for " + paramValue + " is " + searchCount + "</p>");
            out.println("</body>");
            out.println("</html>");
            out.close();
        } else if (searchCount == null && paramValue != null && paramValue.length() == 5) {
            out.println("<!DOCTYPE html>");
            out.println("<html><head>");
            out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
            out.println("<title>Weather Data</title></head>");
            out.println("<body>");
            out.println("<p>You have not searched for " + paramValue + " yet.</p>");
            out.println("</body>");
            out.println("</html>");
            out.close();
        } else {
            out.println("<!DOCTYPE html>");
            out.println("<html><head>");
            out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
            out.println("<title>Weather Data</title></head>");
            out.println("<body>");
            out.println("<p>No valid zip code given. Please search by adding \"?zipcode=\" to the URL, followed by a valid US zip code.</p>");
            out.println("</body>");
            out.println("</html>");
            out.close();
        }
    }
}
