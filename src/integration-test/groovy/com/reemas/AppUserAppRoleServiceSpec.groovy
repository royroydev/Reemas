package com.reemas

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class AppUserAppRoleServiceSpec extends Specification {

    AppUserAppRoleService appUserAppRoleService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new AppUserAppRole(...).save(flush: true, failOnError: true)
        //new AppUserAppRole(...).save(flush: true, failOnError: true)
        //AppUserAppRole appUserAppRole = new AppUserAppRole(...).save(flush: true, failOnError: true)
        //new AppUserAppRole(...).save(flush: true, failOnError: true)
        //new AppUserAppRole(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //appUserAppRole.id
    }

    void "test get"() {
        setupData()

        expect:
        appUserAppRoleService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<AppUserAppRole> appUserAppRoleList = appUserAppRoleService.list(max: 2, offset: 2)

        then:
        appUserAppRoleList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        appUserAppRoleService.count() == 5
    }

    void "test delete"() {
        Long appUserAppRoleId = setupData()

        expect:
        appUserAppRoleService.count() == 5

        when:
        appUserAppRoleService.delete(appUserAppRoleId)
        sessionFactory.currentSession.flush()

        then:
        appUserAppRoleService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        AppUserAppRole appUserAppRole = new AppUserAppRole()
        appUserAppRoleService.save(appUserAppRole)

        then:
        appUserAppRole.id != null
    }
}
