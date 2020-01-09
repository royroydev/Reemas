package com.reemas

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMIN', 'ROLE_USER'])
class Usda3Controller {

    Usda3Service usda3Service

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond usda3Service.list(params), model:[usda3Count: usda3Service.count()]
    }

    def show(Long id) {
        respond usda3Service.get(id)
    }

    def create() {
        respond new Usda3(params)
    }

    def save(Usda3 usda3) {
        if (usda3 == null) {
            notFound()
            return
        }

        try {
            usda3Service.save(usda3)
        } catch (ValidationException e) {
            respond usda3.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'usda3.label', default: 'Usda3'), usda3.id])
                redirect usda3
            }
            '*' { respond usda3, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond usda3Service.get(id)
    }

    def update(Usda3 usda3) {
        if (usda3 == null) {
            notFound()
            return
        }

        try {
            usda3Service.save(usda3)
        } catch (ValidationException e) {
            respond usda3.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'usda3.label', default: 'Usda3'), usda3.id])
                redirect usda3
            }
            '*'{ respond usda3, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        usda3Service.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'usda3.label', default: 'Usda3'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'usda3.label', default: 'Usda3'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
