package com.reemas

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*
@Secured('ROLE_ADMIN')
class AppRoleController {

    AppRoleService appRoleService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond appRoleService.list(params), model:[appRoleCount: appRoleService.count()]
    }

    def show(Long id) {
        respond appRoleService.get(id)
    }

    def create() {
        respond new AppRole(params)
    }

    def save(AppRole appRole) {
        if (appRole == null) {
            notFound()
            return
        }

        try {
            appRoleService.save(appRole)
        } catch (ValidationException e) {
            respond appRole.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'appRole.label', default: 'AppRole'), appRole.id])
                redirect appRole
            }
            '*' { respond appRole, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond appRoleService.get(id)
    }

    def update(AppRole appRole) {
        if (appRole == null) {
            notFound()
            return
        }

        try {
            appRoleService.save(appRole)
        } catch (ValidationException e) {
            respond appRole.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'appRole.label', default: 'AppRole'), appRole.id])
                redirect appRole
            }
            '*'{ respond appRole, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        appRoleService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'appRole.label', default: 'AppRole'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'appRole.label', default: 'AppRole'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
