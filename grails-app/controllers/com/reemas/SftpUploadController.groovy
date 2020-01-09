package com.reemas

import grails.plugin.springsecurity.annotation.Secured

class SftpUploadController {

    def simpleSftpService

    @Secured(['ROLE_ADMIN', 'ROLE_USER'])
    def index() {

    }

    @Secured(['ROLE_ADMIN', 'ROLE_USER'])
    def uploadFile(FeaturedImageCommand cmd){
        String filename = cmd.featuredFile.originalFilename

        if (cmd == null) {
            notFound()
            return
        }

        if (cmd.hasErrors()) {
            respond(cmd.errors, model: [test: cmd], view: 'index')
            return
        }

        // method call on service class
        simpleSftpService.uploadFile(cmd.featuredFile.getInputStream(), filename)

//        flash.message = message(code: 'default.updated.message', args: [message(code: 'reeferData.label', default: 'ReeferData'), reeferData.id])
        respond(cmd, model: [test: cmd], view: 'index')
    }

}
