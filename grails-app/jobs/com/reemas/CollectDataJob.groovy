package com.reemas
import grails.core.GrailsApplication

class CollectDataJob {
    static triggers = {
        simple name:'simpleTrigger', startDelay:5000, repeatInterval: 60000, repeatCount: -1
    }

    def emailReaderService

    def execute() {
        log.info "Start job collect emails"
        emailReaderService.saveMailsToDB()
    }

}
