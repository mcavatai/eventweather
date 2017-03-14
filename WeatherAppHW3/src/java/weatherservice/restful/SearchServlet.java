/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weatherservice.restful;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Mike
 */
public class SearchServlet extends HttpServlet {

//    private class RestRequest {
//
//        private Pattern regExSearchPattern = Pattern.compile("/restful/users/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,6}$/searches/^\\d{5}$");
//
//        public RestRequest(String path) throws ServletException {
//            Matcher matcher;
//            matcher = regExSearchPattern.matcher(path);
//            if (matcher.find()) {
//                
//            }
//            throw new ServletException("Invalid URI");
//        }
//    }
    
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        
    }
}
