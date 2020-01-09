package com.reemas

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMIN', 'ROLE_USER'])
class ActO2Controller {

    ActO2Service actO2Service

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond actO2Service.list(params), model:[actO2Count: actO2Service.count()]
    }

    def show(Long id) {
        respond actO2Service.get(id)
    }

    def create() {
        respond new ActO2(params)
    }

    def save(ActO2 actO2) {
        if (actO2 == null) {
            notFound()
            return
        }

        try {
            actO2Service.save(actO2)
        } catch (ValidationException e) {
            respond actO2.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'actO2.label', default: 'ActO2'), actO2.id])
                redirect actO2
            }
            '*' { respond actO2, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond actO2Service.get(id)
    }

    def update(ActO2 actO2) {
        if (actO2 == null) {
            notFound()
            return
        }

        try {
            actO2Service.save(actO2)
        } catch (ValidationException e) {
            respond actO2.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'actO2.label', default: 'ActO2'), actO2.id])
                redirect actO2
            }
            '*'{ respond actO2, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        actO2Service.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'actO2.label', default: 'ActO2'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'actO2.label', default: 'ActO2'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
