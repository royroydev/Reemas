package com.reemas

import grails.validation.Validateable
import org.springframework.web.multipart.MultipartFile

/**
 * Created by adan on 01/10/18.
 */
class FeaturedImageCommand implements Validateable{
    MultipartFile featuredFile

    static constraints = {

        featuredFile validator : { val, obj ->
            if ( val == null ) {
                return false
            }
            if ( val.empty ) {
                return false
            }

            ['sbd'].any { extension -> // <1>
                val.originalFilename?.toLowerCase()?.endsWith(extension)
            }

        }
    }


}