package helloworld

import groovy.sql.Sql
import org.grails.web.json.JSONObject

import java.sql.SQLException


/**
 * Created by Mike on 4/22/2017.
 */
class SearchService {

    long saveSearchData(String search) throws SQLException {
        def db = [url:'jdbc:mysql://localhost:3306/grailstank', user:'root', password:'smb3pwns', driver:'com.mysql.jdbc.Driver']
        def sql = Sql.newInstance(db.url, db.user, db.password, db.driver)
        //define count variable
        long searchcount = -1
        //check if query is in database
        def searchquery = search
        def ans = sql.firstRow("SELECT * FROM Searches WHERE name =  $searchquery")

        if (null != ans)
            sql.execute("UPDATE Searches SET searchcount = searchcount + 1 WHERE name = $searchquery;")
        else
            sql.execute("INSERT INTO Searches (name, searchcount) values ($searchquery, 1);")
        //no=insert new row, count=1
        //yes=get current count and update
        //return count
        ans = sql.firstRow("SELECT * FROM Searches WHERE name =  $searchquery")
        return ans.searchcount
    }

    void clearAllHistory() throws SQLException {
        def db = [url:'jdbc:mysql://localhost:3306/grailstank', user:'root', password:'smb3pwns', driver:'com.mysql.jdbc.Driver']
        def sql = Sql.newInstance(db.url, db.user, db.password, db.driver)
        sql.execute("DELETE FROM Searches;")
        sql.execute("ALTER TABLE Searches AUTO_INCREMENT=1")
    }

    //RESTFUL
    String getSearchInfo(String name) throws SQLException {
        def db = [url:'jdbc:mysql://localhost:3306/grailstank', user:'root', password:'smb3pwns', driver:'com.mysql.jdbc.Driver']
        def sql = Sql.newInstance(db.url, db.user, db.password, db.driver)
        def results = sql.firstRow("SELECT * FROM Searches where name = $name;")
        return new JSONObject(results).toString()
    }

    String newSearch(JSONObject obj) throws SQLException {
        def db = [url:'jdbc:mysql://localhost:3306/grailstank', user:'root', password:'smb3pwns', driver:'com.mysql.jdbc.Driver']
        def sql = Sql.newInstance(db.url, db.user, db.password, db.driver)
        def jsonObject = obj
        def name = jsonObject.get("name")
        def ans = sql.firstRow("SELECT * FROM Searches WHERE name =  $name")
        if (null == ans) {
            sql.execute("INSERT INTO Searches (name, searchcount) values ($name, 1);")
            return getSearchInfo(name)
        } else {
            return "Hey! There is already an entry for $name in the database! Use a PUT request instead."
        }
    }

    String updateSearch(JSONObject obj) throws SQLException {
        def db = [url:'jdbc:mysql://localhost:3306/grailstank', user:'root', password:'smb3pwns', driver:'com.mysql.jdbc.Driver']
        def sql = Sql.newInstance(db.url, db.user, db.password, db.driver)
        def jsonObject = obj
        def name = jsonObject.get("name")
        def searchcount = jsonObject.get("searchcount")
        def ans = sql.firstRow("SELECT * FROM Searches WHERE name =  $name")
        if (null != ans) {
            sql.execute("UPDATE Searches SET searchcount = $searchcount WHERE name = $name;")
            return getSearchInfo(name)
        } else {
            return "There is no entry for $name in the database yet! Quit fooling around and use a POST request first."
        }
    }

    void deleteSearch(String search) throws SQLException {
        def db = [url:'jdbc:mysql://localhost:3306/grailstank', user:'root', password:'smb3pwns', driver:'com.mysql.jdbc.Driver']
        def sql = Sql.newInstance(db.url, db.user, db.password, db.driver)
        sql.execute("DELETE FROM Searches WHERE name = $search")
    }
}
