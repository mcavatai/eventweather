import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Mike on 4/18/2017.
 */

/**
 *
 * @author Mike
 */
public class OpenWeatherModel {

    private boolean validZip = false;

    private String name = "";
    private String temp = "";
    private String humid = "";
    private String wSpeed = "";
    private String overcast = "";

    private String jsonStrValue;

    public OpenWeatherModel(String zip) throws MalformedURLException, IOException {

        if (zip.length() == 5 && zip.matches("^[0-9]+$")) {
            validZip = true;
            URL openweather = new URL("http://api.openweathermap.org/data/2.5/weather?zip=" + zip + ",us&APPID=b4cdb3d940b23503251ce4883be8c49f");
            BufferedReader inp = new BufferedReader(new InputStreamReader(openweather.openStream()));

            String jsonString = "";
            String inputLine;
            while ((inputLine = inp.readLine()) != null) {
                jsonString += inputLine;
            }
            inp.close();
            jsonStrValue = jsonString;

            JSONObject json;
            JSONObject main;
            JSONObject wind;
            JSONObject clouds;

            try {
                json = new JSONObject(jsonString);
                name = (String) json.get("name");
                main = (JSONObject) json.get("main");
                wind = (JSONObject) json.get("wind");
                clouds = (JSONObject) json.get("clouds");
                temp = (String) main.get("temp").toString();
                humid = (String) main.get("humidity").toString();
                wSpeed = (String) wind.get("speed").toString();
                overcast = (String) clouds.get("all").toString();
            } catch (JSONException ex) {
                System.out.println(ex.toString());
            }
        } else if (zip != null) {
            validZip = true;
            URL openweather = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + zip + ",us&APPID=b4cdb3d940b23503251ce4883be8c49f");
            BufferedReader inp = new BufferedReader(new InputStreamReader(openweather.openStream()));

            String jsonString = "";
            String inputLine;
            while ((inputLine = inp.readLine()) != null) {
                jsonString += inputLine;
            }
            inp.close();
            jsonStrValue = jsonString;

            JSONObject json;
            JSONObject main;
            JSONObject wind;
            JSONObject clouds;

            try {
                json = new JSONObject(jsonString);
                name = (String) json.get("name");
                main = (JSONObject) json.get("main");
                wind = (JSONObject) json.get("wind");
                clouds = (JSONObject) json.get("clouds");
                temp = (String) main.get("temp").toString();
                humid = (String) main.get("humidity").toString();
                wSpeed = (String) wind.get("speed").toString();
                overcast = (String) clouds.get("all").toString();
            } catch (JSONException ex) {
                System.out.println(ex.toString());
            }
        } else {
            validZip = false;
        }
    }

    public boolean getValid() {
        return validZip;
    }

    public String getName() {
        return name;
    }

    public String getTemp() {
        return temp;
    }

    public String getHumidity() {
        return humid;
    }

    public String getWindSpeed() {
        return wSpeed;
    }

    public String getOvercast() {
        return overcast;
    }

    public String getJson() {
        return jsonStrValue;
    }
}
