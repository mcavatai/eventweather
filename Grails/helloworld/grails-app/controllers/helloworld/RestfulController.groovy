package helloworld
/**
 * Created by Mike on 4/20/2017.
 */
import grails.rest.*

@Resource(uri = '/rest/searches', formats=['json', 'xml'])
class RestfulController {

    //define services here?
    SearchService searchService

    def newSearch() {
        render("Adding new search... \n ${searchService.newSearch(request.JSON)}")
    }

    def updateSearch() {
        render("Updating search...  \n ${searchService.updateSearch(request.JSON)}")
    }

    def deleteSearch() {
        searchService.deleteSearch(params.zip)
        render("Data for $params.zip deleted.")
    }

}
