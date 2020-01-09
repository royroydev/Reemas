package com.reemas

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMIN', 'ROLE_USER'])
class Usda2Controller {

    Usda2Service usda2Service

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond usda2Service.list(params), model:[usda2Count: usda2Service.count()]
    }

    def show(Long id) {
        respond usda2Service.get(id)
    }

    def create() {
        respond new Usda2(params)
    }

    def save(Usda2 usda2) {
        if (usda2 == null) {
            notFound()
            return
        }

        try {
            usda2Service.save(usda2)
        } catch (ValidationException e) {
            respond usda2.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'usda2.label', default: 'Usda2'), usda2.id])
                redirect usda2
            }
            '*' { respond usda2, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond usda2Service.get(id)
    }

    def update(Usda2 usda2) {
        if (usda2 == null) {
            notFound()
            return
        }

        try {
            usda2Service.save(usda2)
        } catch (ValidationException e) {
            respond usda2.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'usda2.label', default: 'Usda2'), usda2.id])
                redirect usda2
            }
            '*'{ respond usda2, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        usda2Service.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'usda2.label', default: 'Usda2'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'usda2.label', default: 'Usda2'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
