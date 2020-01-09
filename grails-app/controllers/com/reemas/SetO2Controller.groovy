package com.reemas

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMIN', 'ROLE_USER'])
class SetO2Controller {

    SetO2Service setO2Service

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond setO2Service.list(params), model:[setO2Count: setO2Service.count()]
    }

    def show(Long id) {
        respond setO2Service.get(id)
    }

    def create() {
        respond new SetO2(params)
    }

    def save(SetO2 setO2) {
        if (setO2 == null) {
            notFound()
            return
        }

        try {
            setO2Service.save(setO2)
        } catch (ValidationException e) {
            respond setO2.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'setO2.label', default: 'SetO2'), setO2.id])
                redirect setO2
            }
            '*' { respond setO2, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond setO2Service.get(id)
    }

    def update(SetO2 setO2) {
        if (setO2 == null) {
            notFound()
            return
        }

        try {
            setO2Service.save(setO2)
        } catch (ValidationException e) {
            respond setO2.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'setO2.label', default: 'SetO2'), setO2.id])
                redirect setO2
            }
            '*'{ respond setO2, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        setO2Service.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'setO2.label', default: 'SetO2'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'setO2.label', default: 'SetO2'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
