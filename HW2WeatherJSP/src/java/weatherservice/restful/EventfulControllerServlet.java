/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weatherservice.restful;

import Eventful.Eventful;
import com.evdb.javaapi.EVDBAPIException;
import com.evdb.javaapi.EVDBRuntimeException;
import com.evdb.javaapi.data.Event;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by paulk4ever on 3/23/17.
 */
@WebServlet(name = "EventfulControllerServlet")
public class EventfulControllerServlet extends HttpServlet {
    Eventful eventful = new Eventful();
    List<Event> list = new LinkedList<Event>();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        String eventName = request.getParameter("EventName");

        JSONObject object = new JSONObject();
        if (request.getPathInfo() != null) {
            
            JSONObject returnValue = new JSONObject();

            response.getWriter().write(returnValue.toString());
        }


        try {
            for (Event event : eventful.search(eventName)) {
                list.add(event);
            }
            for (Event event : list) {
                object.put("title",event.getTitle());
                object.put("description",event.getDescription());
                object.put("address",event.getVenueAddress());
                object.put("start time",String.valueOf(event.getStartTime()));
                object.put("end time",String.valueOf(event.getStopTime()));
                response.getWriter().write(object.toString());
            }
        } catch (EVDBRuntimeException e) {
            System.out.println("tying to Connect");
        } catch (EVDBAPIException e) {
            System.out.println("tying to Connect");
        } catch (JSONException e) {
            System.out.println("tying to Connect");
        }

    }
}