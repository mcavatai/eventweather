package helloworld
/**
 * Created by Mike on 4/22/2017.
 */
class Search {
    long id
    String name
    long searchcount

    static mapping = {
    }

    static constraints = {
        id nullable:true
        name nullable:false
        searchcount nullable:true
    }
}
