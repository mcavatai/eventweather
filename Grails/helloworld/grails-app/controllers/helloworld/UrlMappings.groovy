package helloworld

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        /*Application (client)*/
        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
        "/testview"(controller: 'hello', action: 'testview')
        "/weather"(controller: 'hello', action: 'weather')
        "/clrData"(controller: 'hello', action: 'clrData')

        /*RESTful services*/
        "/rest/searches/newSearch"(controller: 'restful', action: 'newSearch', method:'post')
        "/rest/searches/updateSearch"(controller: 'restful', action: 'updateSearch', method:'put')
        "/rest/searches/deleteSearch"(controller: 'restful', action: 'deleteSearch', method:'delete')
    }
}
