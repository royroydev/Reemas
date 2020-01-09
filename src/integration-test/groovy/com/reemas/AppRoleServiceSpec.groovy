package com.reemas

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class AppRoleServiceSpec extends Specification {

    AppRoleService appRoleService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new AppRole(...).save(flush: true, failOnError: true)
        //new AppRole(...).save(flush: true, failOnError: true)
        //AppRole appRole = new AppRole(...).save(flush: true, failOnError: true)
        //new AppRole(...).save(flush: true, failOnError: true)
        //new AppRole(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //appRole.id
    }

    void "test get"() {
        setupData()

        expect:
        appRoleService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<AppRole> appRoleList = appRoleService.list(max: 2, offset: 2)

        then:
        appRoleList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        appRoleService.count() == 5
    }

    void "test delete"() {
        Long appRoleId = setupData()

        expect:
        appRoleService.count() == 5

        when:
        appRoleService.delete(appRoleId)
        sessionFactory.currentSession.flush()

        then:
        appRoleService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        AppRole appRole = new AppRole()
        appRoleService.save(appRole)

        then:
        appRole.id != null
    }
}
