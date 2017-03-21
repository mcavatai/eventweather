/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weatherservice;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
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
public class UserSearchInfoView extends HttpServlet {

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

        String zipParam = request.getParameter("zipcode");
        String emailParam = request.getParameter("email");
        String httpParam = request.getParameter("command");
        if (null != zipParam && null != emailParam && null != httpParam && httpParam.equals("PUT")) {
            //update search info (increment by 1)
            URL url = new URL("http://localhost:8080/WeatherAppHW2/restful/RestfulSearchInfo/" + emailParam + "/" + zipParam);
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setRequestMethod("PUT");
            httpCon.setRequestProperty("Content-Type", "application/json");
            httpCon.setRequestProperty("Accept", "application/json");
            httpCon.setDoOutput(true);
            OutputStreamWriter osw = new OutputStreamWriter(httpCon.getOutputStream());
            osw.write("{\"searchCount\":0,\"userEmail\":[\"mcavatai@oswego.edu\"],\"location\":[\"13090\"]}");
            osw.flush();
            osw.close();
            httpCon.connect();
            httpCon.getResponseCode();

            ServletContext context = getServletContext();
            RequestDispatcher rd = context.getRequestDispatcher("/restful/RestfulSearchInfo/" + emailParam + "/" + zipParam);
            rd.forward(request, response);
        } else if (zipParam != null && emailParam != null) {
            ServletContext context = getServletContext();
            RequestDispatcher rd = context.getRequestDispatcher("/restful/RestfulSearchInfo/" + emailParam + "/" + zipParam);
            rd.forward(request, response);
        }

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("</head>");
        out.println("<body>");
//        out.println(request.getPathInfo());
        out.println("<p>User Search Info Retrieval Service</p>");
        out.println("<form method=\"get\">"
                + "<table width=\"75%\">\n"
                + "                <tr> \n"
                + "                    <td width=\"48%\">What is your zipcode?</td>\n"
                + "                    <td width=\"52%\">\n"
                + "                        <input type=\"text\" name=\"zipcode\" />\n"
                + "                    </td>\n"
                + "                </tr>\n"
                + "                <tr> \n"
                + "                    <td width=\"48%\">What is your email?</td>\n"
                + "                    <td width=\"52%\">\n"
                + "                        <input type=\"text\" name=\"email\" />\n"
                + "                    </td>\n"
                + "                </tr>\n"
                + "<tr>"
                + "  <td width=\"52%\"> <input type=\"checkbox\" name=\"command\" value=\"PUT\">\n"
                + "  <label for=\"cbox1\">Reset search count to 0?</label> </td>\n"
                + "</tr>"
                + "            </table>\n"
                + "            <p> \n"
                + "                <input type=\"submit\" name=\"Submit\" value=\"Request Search Info\" />\n"
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
