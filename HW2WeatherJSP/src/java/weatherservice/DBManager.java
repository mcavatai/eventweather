package weatherservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DBManager {

    Statement st;

    public DBManager() {

        try {
            // The newInstance() call is a work around for some
            // broken Java implementations
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                System.out.println("Error: unable to load driver class!" + ex);
                System.exit(1);
            }

            //Should establish the connection
            Connection conn = DriverManager.getConnection("jdbc:mysql://pi.cs.oswego.edu:22/" + "tank", "zfoster", "csc380");
            st = conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Add a users name, password, and email
    //EMAIL IS THE PRIMARY KEY, this is because it's different for each user
    public boolean addUser(String name, String password, String email) {

        try {
            st.executeUpdate("INSERT into Users VALUES('" + name + "', '" + password + "', '" + email + "');");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //We will use this primarily at login.
    //We search the users by the email and if the password matches, return true (get the okay to log in)
    public boolean findUser(String password, String email) {

        try {
            ResultSet user = st.executeQuery("SELECT * FROM Users WHERE Email = '" + email + "' and Password = '" + password + "';");
            if (user != null) {
                return true;
            }

            return false;

        } catch (Exception e) {
            return false;

        }
    }

    //This is taking the search, and also the user's email
    //adds the search with the user's email to the "Searches" Table
    public boolean addSearch(String search, String userEmail) {
        try {
            st.executeUpdate("INSERT into Users VALUES('" + search + "', '" + userEmail + "', '" + 1 + "');");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //This returns the history of searches for user when given the user's email
    public ArrayList<String> getSearches(String userEmail) {
        ArrayList<String> searchesArray = new ArrayList<>();
        try {
            ResultSet searches = st.executeQuery("SELECT * FROM Searches WHERE User = '" + userEmail + "';");
            while (searches.next()) {
                searchesArray.add(searches.getString("Search"));
            }
            return searchesArray;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean incrementSearchCount(String search, String userEmail) {
        try {
            int searchCount;
            ResultSet searches = st.executeQuery("SELECT * FROM Searches WHERE User = '" + userEmail + "' AND Search = '" + search + "';");
            while (searches.next()) {
                //grab Search_Count here
                searchCount = searches.getInt("Search_Count");
                //increment
                searchCount++;
                //put back into db
                st.executeUpdate("DELETE * FROM Searches WHERE User = '" + userEmail + "' AND Search = '" + search + "';");
                st.executeUpdate("INSERT into Searches VALUES('" + search + "', '" + userEmail + "', '" + searchCount + "');");
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean clearSearchHistory(String userEmail) {
        try {
            st.executeQuery("DELETE * FROM Searches WHERE User = '" + userEmail + "';");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
