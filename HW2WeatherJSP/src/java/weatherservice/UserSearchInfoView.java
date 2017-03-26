/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weatherservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONException;
import org.json.simple.JSONObject;

/**
 *
 * @author Mike
 */
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
            zipParam = zipParam.replace(" ", "_");
            URL url = new URL("http://localhost:8080/WeatherAppHW2/restful/RestfulSearchInfo/" + emailParam + "/" + zipParam);
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));

            String jsonString = "";
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                jsonString += inputLine;
            }
            in.close();

            JSONParser parser = new JSONParser();
            JSONObject json;
            int counter = 0;
            try {
                json = (JSONObject) parser.parse(jsonString);
                counter = ((Long) json.get("searchCount")).intValue();
            } catch (ParseException ex) {
                Logger.getLogger(UserSearchInfoView.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            counter++;
            httpCon.disconnect();
            httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setRequestMethod("PUT");
            httpCon.setRequestProperty("Content-Type", "application/json");
            httpCon.setRequestProperty("Accept", "application/json");
            httpCon.setDoOutput(true);
            OutputStreamWriter osw = new OutputStreamWriter(httpCon.getOutputStream());
            osw.write("{\"searchCount\":" + counter +",\"userEmail\":[\"" + emailParam + "\"],\"location\":[\"" + zipParam + "\"]}");
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
                + "  <label for=\"cbox1\">Increment count?</label> </td>\n"
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
