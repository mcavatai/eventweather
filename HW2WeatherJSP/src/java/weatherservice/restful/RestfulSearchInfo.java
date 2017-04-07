/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weatherservice.restful;

import java.io.BufferedReader;
import java.sql.Connection;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Mike
 */
public class RestfulSearchInfo extends HttpServlet {

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

        if (request.getPathInfo() != null) {
            String[] uriParameters = request.getPathInfo().substring(1).split("/");
            String emailAddress = uriParameters[0];
            String zipCode = uriParameters[1].replace("_", " ");

            JSONObject feedback = new JSONObject();
            int searchCount;

            try {

                try {
                    Class.forName("com.mysql.jdbc.Driver");
                } catch (ClassNotFoundException ex) {
                    System.out.println("Error: unable to load driver class!" + ex);
                    System.exit(1);
                }
                //grab shit from the database!!!
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + "tank", "root", "smb3pwns");
                Statement stmt = conn.createStatement();
                ResultSet searches = stmt.executeQuery("SELECT * FROM Searches WHERE UserEmail = '" + emailAddress + "' AND Search = '" + zipCode + "';");
                while (searches.next()) {
                    //get search count
                    searchCount = searches.getInt("Search_Count");
                    //make ye JSON
                    feedback.put("userEmail", emailAddress);
                    feedback.put("location", zipCode);
                    feedback.put("searchCount", searchCount);
                }
                response.getWriter().write(feedback.toString());
            } catch (SQLException ex) {
                Logger.getLogger(RestfulSearchInfo.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JSONException ex) {
                Logger.getLogger(RestfulSearchInfo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        if (request.getPathInfo() != null) {
            String[] uriParameters = request.getPathInfo().substring(1).split("/");
            String userEmail = uriParameters[0];
            String search = uriParameters[1].replace("_", " ");

            BufferedReader reader = request.getReader();
            StringBuilder builder = new StringBuilder();
            String input;
            while ((input = reader.readLine()) != null) {
                System.out.println(input);
                builder.append(input);
            }

            JSONObject data;
            try {
                data = new JSONObject(builder.toString());

                try {
                    Class.forName("com.mysql.jdbc.Driver");
                } catch (ClassNotFoundException ex) {
                    System.out.println("Error: unable to load driver class!" + ex);
                    System.exit(1);
                }

                int searchCount = -1;
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + "tank", "root", "smb3pwns");
                ResultSet searches = conn.createStatement().executeQuery("SELECT * FROM Searches WHERE UserEmail = '" + userEmail + "' AND Search = '" + search.replace("_", " ") + "';");
                while (searches.next()) {
                    conn.createStatement().executeUpdate("UPDATE Searches SET Search_Count = " + (int) data.get("searchCount") + " WHERE UserEmail = '" + userEmail + "' AND Search = '" + search.replace("_", " ") + "';");
                }
            } catch (JSONException ex) {
                Logger.getLogger(RestfulSearchInfo.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(RestfulSearchInfo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        if (request.getPathInfo() != null) {
            String[] uriParameters = request.getPathInfo().substring(1).split("/");
            String userEmail = uriParameters[0];
            String search = uriParameters[1].replace("_", " ");

            BufferedReader reader = request.getReader();
            StringBuilder builder = new StringBuilder();
            String input;
            while ((input = reader.readLine()) != null) {
                System.out.println("POST: " + input);
                builder.append(input);
            }

            JSONObject data;
            try {
                data = new JSONObject(builder.toString());

                try {
                    Class.forName("com.mysql.jdbc.Driver");
                } catch (ClassNotFoundException ex) {
                    System.out.println("Error: unable to load driver class!" + ex);
                    System.exit(1);
                }

                int searchCount = -1;
                searchCount = (int) data.get("searchCount");
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + "tank", "root", "smb3pwns");
                conn.createStatement().executeUpdate("INSERT into Searches VALUES('" + (int) data.get("searchCount") + "', '" + search + "', '" + userEmail + "');");
            } catch (JSONException ex) {
                Logger.getLogger(RestfulSearchInfo.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(RestfulSearchInfo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
