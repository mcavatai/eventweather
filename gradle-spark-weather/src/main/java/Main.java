import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.*;
import com.j256.ormlite.support.ConnectionSource;
import org.json.JSONObject;
import org.json.JSONString;
import spark.ModelAndView;

public class Main {
    public static void main(String[] args) throws SQLException {
        staticFileLocation("/public");

        String dbUrl = "jdbc:mysql://localhost:3306/sparktank";
        ConnectionSource connectionSource = new JdbcConnectionSource(dbUrl);
        ((JdbcConnectionSource)connectionSource).setUsername("root");
        ((JdbcConnectionSource)connectionSource).setPassword("smb3pwns");

        Dao<Searches,String> searchesDao = DaoManager.createDao(connectionSource, Searches.class);

        get("/hello", (req, res) -> "Hello World");

//        get("/", (request, response) -> {
//            return new ModelAndView(new HashMap(), "templates/hello.vtl");
//        }, new VelocityTemplateEngine());
//
//        get("/favorite_photos", (request, response) -> {
//            return new ModelAndView(new HashMap(), "templates/favorite_photos.vtl");
//        }, new VelocityTemplateEngine());

        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("template", "templates/hello.vtl" );
            model.put("quack", "Quack!");
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());

        get("/favorite_photos", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("template", "templates/favorite_photos.vtl" );
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());

        //WEATHER CLIENT

        get("/weather", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            //TODO Add in the Weather Model and assign objects to map
            String zipcode = request.queryParams("zip");
            if (null != zipcode) {
                DBController controller = new DBController();
                int scount = controller.rowExists(zipcode);
                if (scount >= 0) {
                    model.put("scount", controller.incSearchCount(zipcode, scount));
                } else {
                    model.put("scount", controller.addSearchRow(zipcode));
                }
                OpenWeatherModel weatherModel = new OpenWeatherModel(zipcode);
                model.put("zipcode", zipcode);
                if (weatherModel.getValid()) {
                    model.put("location", weatherModel.getName());
                    model.put("temp", weatherModel.getTemp());
                    model.put("humid", weatherModel.getHumidity());
                    model.put("overcast", weatherModel.getOvercast());
                    model.put("windspd", weatherModel.getWindSpeed());
                }
            }
            //TODO update the database accordingly
            return new ModelAndView(model, "templates/weather.vtl");
        }, new VelocityTemplateEngine());

        get("/weather/deletehistory", (request, response) -> {
//            System.out.println("DELETED!!!");
            new DBController().deleteHistory();
            response.redirect("/weather");
            return null;
        });

        //RESTFUL API

        //Get search data by name
        get("restful/searches/:name", (request, response) -> {
            QueryBuilder<Searches, String> qBuilder = searchesDao.queryBuilder();
            Where<Searches, String> where = qBuilder.where();
            where.eq(Searches.NAME_FIELD, request.params(":name"));
            PreparedQuery<Searches> pQuery = qBuilder.prepare();
            Searches search = searchesDao.queryForFirst(pQuery);
            if (search != null) {
                return ("Search Value: " + search.getName()
                + "\nSearch Count: " + search.getSearchcount()
                + "\n" + new JSONObject(search).toString());
            } else {
                response.status(404);
                return "Not found.";
            }
        });

        //Post new search data
        post("restful/searches/new", (req, res) -> {
            Searches search = new Searches();
            JSONObject obj = new JSONObject(req.body());
            search.setName((String) obj.get("name"));
            search.setSearchcount(1);
            if (new DBController().rowExists(search.getName()) >= 0) {
                return "Row already exists for " + search.getName() + ".";
            } else {
                searchesDao.create(search);
                return "Added new row: \n" + new JSONObject(search).toString();
            }
        });

        //Update search data count
        put("restful/searches/:name/update", (req, res) -> {
            JSONObject obj = new JSONObject(req.body());
            int scount = obj.getInt("searchcount");

            UpdateBuilder<Searches, String> uBuilder = searchesDao.updateBuilder();
            uBuilder.updateColumnValue("searchcount", scount);
            uBuilder.where().eq(Searches.NAME_FIELD, req.params(":name"));
            uBuilder.update();
            return "Updating row... \n" + new DBController().getRowJSON(req.params(":name"));
        });

        //Delete data for selected search query
        delete("restful/searches/:name/delete", (req, res) -> {
            DeleteBuilder<Searches, String> deleteBuilder = searchesDao.deleteBuilder();
            deleteBuilder.where().eq(Searches.NAME_FIELD, req.params(":name"));
            if (deleteBuilder.delete() > 0) {
                return "Row for " + req.params(":name") + " deleted.";
            } else {
                return "No entry for " + req.params(":name") + " found.";
            }
        });
    }
}