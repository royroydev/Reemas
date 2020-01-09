package com.reemas

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class ActCO2ServiceSpec extends Specification {

    ActCO2Service actCO2Service
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new ActCO2(...).save(flush: true, failOnError: true)
        //new ActCO2(...).save(flush: true, failOnError: true)
        //ActCO2 actCO2 = new ActCO2(...).save(flush: true, failOnError: true)
        //new ActCO2(...).save(flush: true, failOnError: true)
        //new ActCO2(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //actCO2.id
    }

    void "test get"() {
        setupData()

        expect:
        actCO2Service.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<ActCO2> actCO2List = actCO2Service.list(max: 2, offset: 2)

        then:
        actCO2List.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        actCO2Service.count() == 5
    }

    void "test delete"() {
        Long actCO2Id = setupData()

        expect:
        actCO2Service.count() == 5

        when:
        actCO2Service.delete(actCO2Id)
        sessionFactory.currentSession.flush()

        then:
        actCO2Service.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        ActCO2 actCO2 = new ActCO2()
        actCO2Service.save(actCO2)

        then:
        actCO2.id != null
    }
}
