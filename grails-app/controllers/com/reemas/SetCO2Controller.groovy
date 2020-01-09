package com.reemas

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMIN', 'ROLE_USER'])
class SetCO2Controller {

    SetCO2Service setCO2Service

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond setCO2Service.list(params), model:[setCO2Count: setCO2Service.count()]
    }

    def show(Long id) {
        respond setCO2Service.get(id)
    }

    def create() {
        respond new SetCO2(params)
    }

    def save(SetCO2 setCO2) {
        if (setCO2 == null) {
            notFound()
            return
        }

        try {
            setCO2Service.save(setCO2)
        } catch (ValidationException e) {
            respond setCO2.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'setCO2.label', default: 'SetCO2'), setCO2.id])
                redirect setCO2
            }
            '*' { respond setCO2, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond setCO2Service.get(id)
    }

    def update(SetCO2 setCO2) {
        if (setCO2 == null) {
            notFound()
            return
        }

        try {
            setCO2Service.save(setCO2)
        } catch (ValidationException e) {
            respond setCO2.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'setCO2.label', default: 'SetCO2'), setCO2.id])
                redirect setCO2
            }
            '*'{ respond setCO2, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        setCO2Service.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'setCO2.label', default: 'SetCO2'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'setCO2.label', default: 'SetCO2'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
