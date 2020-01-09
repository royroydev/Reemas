package com.reemas

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

@Secured('ROLE_ADMIN')
class AppUserAppRoleController {

    AppUserAppRoleService appUserAppRoleService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        def roleUser = appUserAppRoleService.list(params)
        def retrieve
        respond appUserAppRoleService.list(params), model:[appUserAppRoleCount: appUserAppRoleService.count(), roleUser: roleUser ]
    }

    def show(Long id) {
        def userRole = AppUserAppRole.get(Long.valueOf(params.idUser), Long.valueOf(params.idRole))

        respond model:[userRole: AppUserAppRole.get(Long.valueOf(params.idUser), Long.valueOf(params.idRole)) ]
    }

    def create() {
        respond new AppUserAppRole(params)
    }

    def save(AppUserAppRole appUserAppRole) {
        if (appUserAppRole == null) {
            notFound()
            return
        }

        try {
            appUserAppRoleService.save(appUserAppRole)
        } catch (ValidationException e) {
            respond appUserAppRole.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'appUserAppRole.label', default: 'AppUserAppRole'), appUserAppRole.id])
                redirect appUserAppRole
            }
            '*' { respond appUserAppRole, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond appUserAppRoleService.get(id)
    }

    def update(AppUserAppRole appUserAppRole) {
        if (appUserAppRole == null) {
            notFound()
            return
        }

        try {
            appUserAppRoleService.save(appUserAppRole)
        } catch (ValidationException e) {
            respond appUserAppRole.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'appUserAppRole.label', default: 'AppUserAppRole'), appUserAppRole.id])
                redirect appUserAppRole
            }
            '*'{ respond appUserAppRole, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        appUserAppRoleService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'appUserAppRole.label', default: 'AppUserAppRole'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'appUserAppRole.label', default: 'AppUserAppRole'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
