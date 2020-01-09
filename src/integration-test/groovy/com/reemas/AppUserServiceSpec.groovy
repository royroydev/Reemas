package com.reemas

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class AppUserServiceSpec extends Specification {

    AppUserService appUserService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new AppUser(...).save(flush: true, failOnError: true)
        //new AppUser(...).save(flush: true, failOnError: true)
        //AppUser appUser = new AppUser(...).save(flush: true, failOnError: true)
        //new AppUser(...).save(flush: true, failOnError: true)
        //new AppUser(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //appUser.id
    }

    void "test get"() {
        setupData()

        expect:
        appUserService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<AppUser> appUserList = appUserService.list(max: 2, offset: 2)

        then:
        appUserList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        appUserService.count() == 5
    }

    void "test delete"() {
        Long appUserId = setupData()

        expect:
        appUserService.count() == 5

        when:
        appUserService.delete(appUserId)
        sessionFactory.currentSession.flush()

        then:
        appUserService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        AppUser appUser = new AppUser()
        appUserService.save(appUser)

        then:
        appUser.id != null
    }
}
