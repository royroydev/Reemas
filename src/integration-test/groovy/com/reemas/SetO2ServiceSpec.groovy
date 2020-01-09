package com.reemas

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class SetO2ServiceSpec extends Specification {

    SetO2Service setO2Service
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new SetO2(...).save(flush: true, failOnError: true)
        //new SetO2(...).save(flush: true, failOnError: true)
        //SetO2 setO2 = new SetO2(...).save(flush: true, failOnError: true)
        //new SetO2(...).save(flush: true, failOnError: true)
        //new SetO2(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //setO2.id
    }

    void "test get"() {
        setupData()

        expect:
        setO2Service.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<SetO2> setO2List = setO2Service.list(max: 2, offset: 2)

        then:
        setO2List.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        setO2Service.count() == 5
    }

    void "test delete"() {
        Long setO2Id = setupData()

        expect:
        setO2Service.count() == 5

        when:
        setO2Service.delete(setO2Id)
        sessionFactory.currentSession.flush()

        then:
        setO2Service.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        SetO2 setO2 = new SetO2()
        setO2Service.save(setO2)

        then:
        setO2.id != null
    }
}
