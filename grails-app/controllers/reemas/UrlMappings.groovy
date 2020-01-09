package reemas

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }
//
//        name datalist: "/reeferData/index" {
//            controller = 'reeferData'
//            action = 'renderList'
//        }

        "/"(controller: "reeferData")

        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
