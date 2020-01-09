package com.reemas

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class SetPointServiceSpec extends Specification {

    SetPointService setPointService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new SetPoint(...).save(flush: true, failOnError: true)
        //new SetPoint(...).save(flush: true, failOnError: true)
        //SetPoint setPoint = new SetPoint(...).save(flush: true, failOnError: true)
        //new SetPoint(...).save(flush: true, failOnError: true)
        //new SetPoint(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //setPoint.id
    }

    void "test get"() {
        setupData()

        expect:
        setPointService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<SetPoint> setPointList = setPointService.list(max: 2, offset: 2)

        then:
        setPointList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        setPointService.count() == 5
    }

    void "test delete"() {
        Long setPointId = setupData()

        expect:
        setPointService.count() == 5

        when:
        setPointService.delete(setPointId)
        sessionFactory.currentSession.flush()

        then:
        setPointService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        SetPoint setPoint = new SetPoint()
        setPointService.save(setPoint)

        then:
        setPoint.id != null
    }
}
