package com.reemas

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMIN', 'ROLE_USER'])
class SetPointController {

    SetPointService setPointService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond setPointService.list(params), model:[setPointCount: setPointService.count()]
    }

    def show(Long id) {
        respond setPointService.get(id)
    }

    def create() {
        respond new SetPoint(params)
    }

    def save(SetPoint setPoint) {
        if (setPoint == null) {
            notFound()
            return
        }

        try {
            setPointService.save(setPoint)
        } catch (ValidationException e) {
            respond setPoint.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'setPoint.label', default: 'SetPoint'), setPoint.id])
                redirect setPoint
            }
            '*' { respond setPoint, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond setPointService.get(id)
    }

    def update(SetPoint setPoint) {
        if (setPoint == null) {
            notFound()
            return
        }

        try {
            setPointService.save(setPoint)
        } catch (ValidationException e) {
            respond setPoint.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'setPoint.label', default: 'SetPoint'), setPoint.id])
                redirect setPoint
            }
            '*'{ respond setPoint, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        setPointService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'setPoint.label', default: 'SetPoint'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'setPoint.label', default: 'SetPoint'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
