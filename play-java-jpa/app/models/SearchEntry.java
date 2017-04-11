package models;

import java.util.*;
import javax.persistence.*;
import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;
import com.avaje.ebean.Model;

@Entity
@Table(name = "PlaySearches")
public class SearchEntry extends Model {
    @Id
    public Long id;

    @Column
    @Constraints.Required
    public String name;

    @Column
    @Constraints.Required
    public Long searchcount;

    public static Finder<Long, SearchEntry> find = new Finder<>(
      Long.class, SearchEntry.class
    );

    public Long getId(){
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSearchcount() {
        return searchcount;
    }

    public void setSearchcount(Long count) {
        this.searchcount = count;
    }
}