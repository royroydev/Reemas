package com.reemas

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*
@Secured(['ROLE_ADMIN', 'ROLE_USER'])
class ActCO2Controller {

    ActCO2Service actCO2Service

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond actCO2Service.list(params), model:[actCO2Count: actCO2Service.count()]
    }

    def show(Long id) {
        respond actCO2Service.get(id)
    }

    def create() {
        respond new ActCO2(params)
    }

    def save(ActCO2 actCO2) {
        if (actCO2 == null) {
            notFound()
            return
        }

        try {
            actCO2Service.save(actCO2)
        } catch (ValidationException e) {
            respond actCO2.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'actCO2.label', default: 'ActCO2'), actCO2.id])
                redirect actCO2
            }
            '*' { respond actCO2, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond actCO2Service.get(id)
    }

    def update(ActCO2 actCO2) {
        if (actCO2 == null) {
            notFound()
            return
        }

        try {
            actCO2Service.save(actCO2)
        } catch (ValidationException e) {
            respond actCO2.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'actCO2.label', default: 'ActCO2'), actCO2.id])
                redirect actCO2
            }
            '*'{ respond actCO2, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        actCO2Service.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'actCO2.label', default: 'ActCO2'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'actCO2.label', default: 'ActCO2'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
