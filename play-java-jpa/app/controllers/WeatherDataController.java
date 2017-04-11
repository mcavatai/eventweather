package controllers;

import play.data.FormFactory;
import play.data.DynamicForm;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import models.WeatherModel;
import models.SearchEntry;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import org.json.*;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

public class WeatherDataController extends Controller {
    private final FormFactory formFactory;
    private final WeatherModel weatherModel;
    private final HttpExecutionContext ec;

    @Inject
    public WeatherDataController(FormFactory formFactory, WeatherModel weatherModel, HttpExecutionContext ec) {
        this.formFactory = formFactory;
        this.weatherModel = weatherModel;
        this.ec = ec;
    }

    public Result index() {
        return ok(views.html.index.render());
    }

    public Result getWeather() throws MalformedURLException, IOException {
        DynamicForm requestData = formFactory.form().bindFromRequest();
        String location = requestData.get("location");
        if (location != null) {
            WeatherModel model = new WeatherModel(location);
            if (model.getValid()) {
                String result = ("Location: " + model.getName());
                result += ("\nCurrent Temp: " + model.getTemp() + "K");
                result += ("\nCurrent Humidity: " + model.getHumidity() + "%");
                result += ("\nCurrent Wind Speed: " + model.getWindSpeed() + "m/s");
                result += ("\nCurrent Overcast: " + model.getOvercast() + "%");
                if (session(location) == null) {
                    session(location, "1");
                    result += ("\n# of Searches: " + Integer.parseInt(session(location)));
                } else {
                    int count = Integer.parseInt(session(location));
                    count++;
                    result += ("\n# of Searches: " + count);
                    session(location, Integer.toString(count));
                }
                return ok(result);
            } else return null;
        } else return null;
    }

    public Result clearHistory() {
        DynamicForm requestData = formFactory.form().bindFromRequest();
        String location = requestData.get("location");
        if (location != null) {
            if (session(location) == null) {
                return ok("No searches exist for this location!");
            } else {
                session().remove(location);
                return ok("Search counter cleared for " + location);
            }
        } else return null;
    }

    public Result clearAllHistory() {
        session().clear();
        return ok("Session data cleared.");
    }

    /* RESTFUL CONTROLS */

    //TODO setup JSON payload

    //POST
    public Result postNewSearchEntry(String name) {
        System.out.println("::POST::");
        System.out.println(name);
        System.out.println(request().body().asJson().toString());
        JsonNode json = request().body().asJson();
        String name2 = json.findPath("name").textValue();
        System.out.println(name2);
        if (SearchEntry.find.where().eq("name", name).findUnique() == null) {
            SearchEntry entry = new SearchEntry();
            entry.name = name;
            entry.searchcount = 1L;
            entry.save();
            return ok("New search data entry posted: " + name);
        } else {
            return ok("Entry for " + name + " already exists!");
        }
    }

    //PUT
    public Result putUpdatedSearchCount(String name) {
        System.out.println("::PUT::");
        System.out.println(name);
        System.out.println(request().body().asJson().toString());
        JsonNode json = request().body().asJson();
        String name2 = json.findPath("name").textValue();
        Long sc2 = json.findPath("searchcount").longValue();
        System.out.println("PAYLOAD:" + name2 + ":" + sc2);
        SearchEntry toBeUpdated = SearchEntry.find.where().eq("name", name).findUnique();
        if (toBeUpdated != null) {
            toBeUpdated.setSearchcount(sc2);
            toBeUpdated.update();
            return ok("Updated count for " + name + " to " + sc2 + ".");
        } else {
            return ok("No search data was found for " + name + "!");
        }
    }

    //DELETE
    public Result deleteSearchEntry(String name) {
        System.out.println("::DELETE::");
        System.out.println(name);
        SearchEntry toBeUpdated = SearchEntry.find.where().eq("name", name).findUnique();
        if (toBeUpdated != null) {
            toBeUpdated.delete();
            return ok("Search data for " + name + " cleared.");
        } else {
            return ok("No search data was found for " + name + "!");
        }
    }
}