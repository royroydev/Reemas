package com.reemas


import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class ReeferDataController {

    ReeferDataService reeferDataService
    QuickSearchService quickSearchService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    @Secured(['ROLE_ADMIN', 'ROLE_USER'])
    def index(Integer max) {
        def query =""
        params.max = Math.min(max ?: 100, 1000)

        if ( params.query ) {
            query = params.query
        }

        def searchParams = [sort: (params.sort ?: 'id'), order: (params.order ?: 'desc'), max: params.max, offset:(params.offset ?: '0')]
        def searchProperties = [containerId:'containerId', containerManufacturer: "containerManufacturer", satelliteId: "satelliteId"]

        def reeferList = quickSearchService.search(domainClass: ReeferData, searchParams: searchParams,
                searchProperties: searchProperties, query: query, tokenizeNumbers: false)

        if (request.xhr) {
            render(template: "listTemplate", model:[reeferDataList: reeferList, reeferDataCount: reeferList.getTotalCount()])
        }
        else {
            respond reeferList, model:[reeferDataCount: reeferList.getTotalCount(), reeferDataList: reeferList]
        }
    }

    @Secured(value=["hasRole('ROLE_ADMIN')"])
    def show(Long id) {
        respond reeferDataService.get(id)
    }

    @Secured(value=["hasRole('ROLE_ADMIN')"])
    def create() {
        respond new ReeferData(params)
    }


    @Secured(value=["hasRole('ROLE_ADMIN')"])
    def save(ReeferData reeferData) {
        if (reeferData == null) {
            notFound()
            return
        }

        try {
            reeferDataService.save(reeferData)
        } catch (ValidationException e) {
            respond reeferData.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'reeferData.label', default: 'ReeferData'), reeferData.id])
                redirect reeferData
            }
            '*' { respond reeferData, [status: CREATED] }
        }
    }

    @Secured(value=["hasRole('ROLE_ADMIN')"])
    def edit(Long id) {
        respond reeferDataService.get(id)
    }

    @Secured(value=["hasRole('ROLE_ADMIN')"])
    def update(ReeferData reeferData) {
        if (reeferData == null) {
            notFound()
            return
        }

        try {
            reeferDataService.save(reeferData)
        } catch (ValidationException e) {
            respond reeferData.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'reeferData.label', default: 'ReeferData'), reeferData.id])
                redirect reeferData
            }
            '*'{ respond reeferData, [status: OK] }
        }
    }

    @Secured(value=["hasRole('ROLE_ADMIN')"])
    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        reeferDataService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'reeferData.label', default: 'ReeferData'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'reeferData.label', default: 'ReeferData'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
