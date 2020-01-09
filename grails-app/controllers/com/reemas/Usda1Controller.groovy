package com.reemas

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMIN', 'ROLE_USER'])
class Usda1Controller {

    Usda1Service usda1Service

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond usda1Service.list(params), model:[usda1Count: usda1Service.count()]
    }

    def show(Long id) {
        respond usda1Service.get(id)
    }

    def create() {
        respond new Usda1(params)
    }

    def save(Usda1 usda1) {
        if (usda1 == null) {
            notFound()
            return
        }

        try {
            usda1Service.save(usda1)
        } catch (ValidationException e) {
            respond usda1.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'usda1.label', default: 'Usda1'), usda1.id])
                redirect usda1
            }
            '*' { respond usda1, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond usda1Service.get(id)
    }

    def update(Usda1 usda1) {
        if (usda1 == null) {
            notFound()
            return
        }

        try {
            usda1Service.save(usda1)
        } catch (ValidationException e) {
            respond usda1.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'usda1.label', default: 'Usda1'), usda1.id])
                redirect usda1
            }
            '*'{ respond usda1, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        usda1Service.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'usda1.label', default: 'Usda1'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'usda1.label', default: 'Usda1'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
