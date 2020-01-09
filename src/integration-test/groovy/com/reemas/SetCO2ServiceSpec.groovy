package com.reemas

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class SetCO2ServiceSpec extends Specification {

    SetCO2Service setCO2Service
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new SetCO2(...).save(flush: true, failOnError: true)
        //new SetCO2(...).save(flush: true, failOnError: true)
        //SetCO2 setCO2 = new SetCO2(...).save(flush: true, failOnError: true)
        //new SetCO2(...).save(flush: true, failOnError: true)
        //new SetCO2(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //setCO2.id
    }

    void "test get"() {
        setupData()

        expect:
        setCO2Service.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<SetCO2> setCO2List = setCO2Service.list(max: 2, offset: 2)

        then:
        setCO2List.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        setCO2Service.count() == 5
    }

    void "test delete"() {
        Long setCO2Id = setupData()

        expect:
        setCO2Service.count() == 5

        when:
        setCO2Service.delete(setCO2Id)
        sessionFactory.currentSession.flush()

        then:
        setCO2Service.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        SetCO2 setCO2 = new SetCO2()
        setCO2Service.save(setCO2)

        then:
        setCO2.id != null
    }
}
