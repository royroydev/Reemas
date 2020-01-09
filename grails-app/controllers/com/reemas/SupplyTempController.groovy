package com.reemas

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMIN', 'ROLE_USER'])
class SupplyTempController {

    SupplyTempService supplyTempService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond supplyTempService.list(params), model:[supplyTempCount: supplyTempService.count()]
    }

    def show(Long id) {
        respond supplyTempService.get(id)
    }

    def create() {
        respond new SupplyTemp(params)
    }

    def save(SupplyTemp supplyTemp) {
        if (supplyTemp == null) {
            notFound()
            return
        }

        try {
            supplyTempService.save(supplyTemp)
        } catch (ValidationException e) {
            respond supplyTemp.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'supplyTemp.label', default: 'SupplyTemp'), supplyTemp.id])
                redirect supplyTemp
            }
            '*' { respond supplyTemp, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond supplyTempService.get(id)
    }

    def update(SupplyTemp supplyTemp) {
        if (supplyTemp == null) {
            notFound()
            return
        }

        try {
            supplyTempService.save(supplyTemp)
        } catch (ValidationException e) {
            respond supplyTemp.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'supplyTemp.label', default: 'SupplyTemp'), supplyTemp.id])
                redirect supplyTemp
            }
            '*'{ respond supplyTemp, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        supplyTempService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'supplyTemp.label', default: 'SupplyTemp'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'supplyTemp.label', default: 'SupplyTemp'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
