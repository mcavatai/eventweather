package Eventful;

import com.evdb.javaapi.APIConfiguration;
import com.evdb.javaapi.EVDBAPIException;
import com.evdb.javaapi.EVDBRuntimeException;
import com.evdb.javaapi.data.Event;
import com.evdb.javaapi.data.Location;
import com.evdb.javaapi.data.request.EventSearchRequest;
import com.evdb.javaapi.operations.EventOperations;

import java.util.List;

/**
 * Created by paulk4ever on 2/19/17.
 */
public class Eventful {

    final private String API_KEY = "qX2dNH9TZGLvBT8B";
    final private String USER_NAME = "events420";
    final private String PASSWORD = "pass55word";

    public EventOperations eventOperations;
    public EventSearchRequest eventSearchRequest;
    public Location location;

    public Eventful() {
        APIConfiguration apiConfiguration = new APIConfiguration();
        apiConfiguration.setApiKey(API_KEY);
        apiConfiguration.setEvdbUser(USER_NAME);
        apiConfiguration.setEvdbPassword(PASSWORD);

        eventOperations = new EventOperations();
        eventSearchRequest = new EventSearchRequest();
        eventSearchRequest.setPageSize(10);
        location = new Location();
        location.setCountry("United States");
        eventSearchRequest.setCategory("soccer");
    }

    public List<Event> search(String keyWord) throws EVDBRuntimeException, EVDBAPIException {
        eventSearchRequest.setKeywords(keyWord);
        eventSearchRequest.setLocation(location.getCountry());

        return eventOperations.search(eventSearchRequest).getEvents();
    }
}
