import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Mike on 5/4/2017.
 */
@DatabaseTable(tableName = "searches")
public class Searches {

    public static final String NAME_FIELD = "name";
    public static final String ID_FIELD = "id";
    public static final String SEARCHCOUNT_FIELD = "searchcount";

    public Searches() {}

    @DatabaseField(generatedId = true, columnName = ID_FIELD)
    private int id;

    @DatabaseField(columnName = NAME_FIELD)
    private String name;

    @DatabaseField(columnName = SEARCHCOUNT_FIELD)
    private int searchcount;

    public int getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setSearchcount(int searchcount) {
        this.searchcount = searchcount;
    }

    public int getSearchcount() {
        return this.searchcount;
    }
}
