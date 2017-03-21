/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weatherservice;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Mike
 */
public class WeatherRestView extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        //if zip is here, forward to the other page
        String param = request.getParameter("zip");
        if (param != null) {
//            response.sendRedirect("/WeatherService/restful/RestfulWeather/" + param);
//TODO Okay so like. This. But stream the shit in from the JSON page
            ServletContext context = getServletContext();
            RequestDispatcher rd = context.getRequestDispatcher("/restful/RestfulWeather/" + param);
            rd.forward(request, response);
        }

        //ask for /restful/users/(email address)/searches/(zipcode)
        //get JSON representing row from Search data table
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("</head>");
        out.println("<body>");
//        out.println(request.getPathInfo());
        out.println("<p>Weather Info Retrieval Service</p>");
        out.println("<form method=\"get\">"
                + "<table width=\"75%\">\n"
                + "                <tr> \n"
                + "                    <td width=\"48%\">What is your zipcode?</td>\n"
                + "                    <td width=\"52%\">\n"
                + "                        <input type=\"text\" name=\"zip\" />\n"
                + "                    </td>\n"
                + "                </tr>\n"
                + "            </table>\n"
                + "            <p> \n"
                + "                <input type=\"submit\" name=\"Submit\" value=\"Get weather info\" />\n"
                + "                <input type=\"reset\" name=\"Reset\" value=\"Reset form\" />\n"
                + "            </p>"
                + "</form>");
//        String zipInfo = request.getParameter("zip");
//        if (zipInfo != null) {
//            //TODO call controllers here plz
//            
//        }
        out.println("</body>");
        out.println("</html>");
    }
}
