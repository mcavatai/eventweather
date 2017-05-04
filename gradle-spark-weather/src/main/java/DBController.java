import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import org.json.JSONObject;

import java.sql.SQLException;

/**
 * Created by Mike on 5/4/2017.
 */
public class DBController {
    //UTILITY FUNCTIONS
    public int rowExists(String name) throws SQLException {
        String dbUrl = "jdbc:mysql://localhost:3306/sparktank";
        ConnectionSource connectionSource = new JdbcConnectionSource(dbUrl);
        ((JdbcConnectionSource)connectionSource).setUsername("root");
        ((JdbcConnectionSource)connectionSource).setPassword("smb3pwns");

        Dao<Searches,String> searchesDao = DaoManager.createDao(connectionSource, Searches.class);

        QueryBuilder<Searches, String> qBuilder = searchesDao.queryBuilder();
        Where<Searches, String> where = qBuilder.where();
        where.eq(Searches.NAME_FIELD, name);
        PreparedQuery<Searches> pQuery = qBuilder.prepare();
        Searches search = searchesDao.queryForFirst(pQuery);
        if (search != null)
            return search.getSearchcount();
        else
            return -1;
    }

    public int addSearchRow(String name) throws SQLException {
        String dbUrl = "jdbc:mysql://localhost:3306/sparktank";
        ConnectionSource connectionSource = new JdbcConnectionSource(dbUrl);
        ((JdbcConnectionSource)connectionSource).setUsername("root");
        ((JdbcConnectionSource)connectionSource).setPassword("smb3pwns");

        Dao<Searches,String> searchesDao = DaoManager.createDao(connectionSource, Searches.class);

        Searches newSearch = new Searches();
        newSearch.setName(name);
        newSearch.setSearchcount(1);

        searchesDao.create(newSearch);
        return 1;
    }

    public int incSearchCount(String name, int scount) throws SQLException {
        String dbUrl = "jdbc:mysql://localhost:3306/sparktank";
        ConnectionSource connectionSource = new JdbcConnectionSource(dbUrl);
        ((JdbcConnectionSource)connectionSource).setUsername("root");
        ((JdbcConnectionSource)connectionSource).setPassword("smb3pwns");

        Dao<Searches,String> searchesDao = DaoManager.createDao(connectionSource, Searches.class);

        UpdateBuilder<Searches, String> uBuilder = searchesDao.updateBuilder();
        uBuilder.updateColumnValue("searchcount", (scount + 1));
        uBuilder.where().eq(Searches.NAME_FIELD, name);
        uBuilder.update();
        return scount + 1;
    }

    public void deleteHistory() throws SQLException {
        String dbUrl = "jdbc:mysql://localhost:3306/sparktank";
        ConnectionSource connectionSource = new JdbcConnectionSource(dbUrl);
        ((JdbcConnectionSource)connectionSource).setUsername("root");
        ((JdbcConnectionSource)connectionSource).setPassword("smb3pwns");

        Dao<Searches,String> searchesDao = DaoManager.createDao(connectionSource, Searches.class);

        searchesDao.deleteBuilder().delete();
    }

    public String getRowJSON(String name) throws SQLException {
        String dbUrl = "jdbc:mysql://localhost:3306/sparktank";
        ConnectionSource connectionSource = new JdbcConnectionSource(dbUrl);
        ((JdbcConnectionSource)connectionSource).setUsername("root");
        ((JdbcConnectionSource)connectionSource).setPassword("smb3pwns");

        Dao<Searches,String> searchesDao = DaoManager.createDao(connectionSource, Searches.class);

        QueryBuilder<Searches, String> qBuilder = searchesDao.queryBuilder();
        Where<Searches, String> where = qBuilder.where();
        where.eq(Searches.NAME_FIELD, name);
        PreparedQuery<Searches> pQuery = qBuilder.prepare();
        Searches search = searchesDao.queryForFirst(pQuery);
        if (search != null)
            return new JSONObject(search).toString();
        else
            return "No row found for " + name + ".";
    }
}
