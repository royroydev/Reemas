package com.reemas

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class ActO2ServiceSpec extends Specification {

    ActO2Service actO2Service
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new ActO2(...).save(flush: true, failOnError: true)
        //new ActO2(...).save(flush: true, failOnError: true)
        //ActO2 actO2 = new ActO2(...).save(flush: true, failOnError: true)
        //new ActO2(...).save(flush: true, failOnError: true)
        //new ActO2(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //actO2.id
    }

    void "test get"() {
        setupData()

        expect:
        actO2Service.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<ActO2> actO2List = actO2Service.list(max: 2, offset: 2)

        then:
        actO2List.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        actO2Service.count() == 5
    }

    void "test delete"() {
        Long actO2Id = setupData()

        expect:
        actO2Service.count() == 5

        when:
        actO2Service.delete(actO2Id)
        sessionFactory.currentSession.flush()

        then:
        actO2Service.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        ActO2 actO2 = new ActO2()
        actO2Service.save(actO2)

        then:
        actO2.id != null
    }
}
