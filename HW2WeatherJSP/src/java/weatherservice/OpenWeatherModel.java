/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weatherservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

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

    public OpenWeatherModel(String zip) throws MalformedURLException, IOException {
        addUser();
        if (zip.length() == 5) {
            validZip = true;
            URL openweather = new URL("http://api.openweathermap.org/data/2.5/weather?zip=" + zip + ",us&APPID=b4cdb3d940b23503251ce4883be8c49f");
            BufferedReader in = new BufferedReader(new InputStreamReader(openweather.openStream()));

            String jsonString = "";
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                jsonString += inputLine;
            }
            in.close();

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
    
    public void addUser() {
        DBManager manager = new DBManager();
        manager.addUser("test", "test", "testmail@test.com");
    }

    public int incSearchCount(String zip) {
        DBManager manager = new DBManager();
        ArrayList<String> searches = manager.getSearches("testmail@test.com");
        if (!searches.isEmpty()) {
            if (searches.contains(zip)) {
                return manager.incrementSearchCount(zip, "testmail@test.com");
            } else {
                manager.addSearch(zip, "testmail@test.com");
                return 1;
            }
        } else {
            manager.addSearch(zip, "testmail@test.com");
            return 1;
        }
    }
}
