package com.reemas

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*
@Secured('ROLE_ADMIN')
class AppUserController {

    AppUserService appUserService
    AppUserAppRoleService appUserAppRoleService
    AppRoleService appRoleService


    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        def roleUser = appUserAppRoleService.list(params)
        respond appUserService.list(params), model:[appUserCount: appUserService.count(), roleUser:roleUser]
    }

    def show(Long id) {
        def appUser = appUserService.get(id)
        def roleUser = AppUserAppRole.findByAppUser(appUser)

        respond appUser, model:[roleUser: roleUser]
    }

    def create() {
        def roles = appRoleService.list()
        respond new AppUser(params), model:[roles: roles]
    }

    def save(AppUser appUser) {
        if (appUser == null) {
            notFound()
            return
        }

        try {
            appUserService.save(appUser)
            def role = appRoleService.get(params.role)
            AppUserAppRole.create(appUser,role).save(flush:true, failOnError:true)

        } catch (ValidationException e) {
            respond appUser.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'appUser.label', default: 'AppUser'), appUser.id])
                redirect appUser
            }
            '*' { respond appUser, [status: CREATED] }
        }
    }

    def edit(Long id) {
        def appUser = appUserService.get(id)
        def roleUser = AppUserAppRole.findByAppUser(appUser)
        def roles = appRoleService.list()
        respond appUser, model:[roleUser: roleUser,  roles: roles]
    }

    def update(AppUser appUser) {
        log.info  "params " + params

        def roleUser = AppUserAppRole.findByAppUser(appUser)


        if (appUser == null) {
            notFound()
            return
        }

        try {
            appUserService.save(appUser)
            if (Long.valueOf(params.role) != roleUser.appRole.id){
                def role = appRoleService.get(params.role)
                def del = appUserAppRoleService.delete(roleUser)
                AppUserAppRole.create(appUser,role).save(flush:true, failOnError:true)
            }
        } catch (ValidationException e) {
            respond appUser.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'appUser.label', default: 'AppUser'), appUser.id])
                redirect appUser
            }
            '*'{ respond appUser, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        def appUser = appUserService.get(id)

        def roleUser = AppUserAppRole.findByAppUser(appUser)
        def del = appUserAppRoleService.delete(roleUser)

        appUserService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'appUser.label', default: 'AppUser'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'appUser.label', default: 'AppUser'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
