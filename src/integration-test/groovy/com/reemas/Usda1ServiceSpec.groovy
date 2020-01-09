package com.reemas

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class Usda1ServiceSpec extends Specification {

    Usda1Service usda1Service
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Usda1(...).save(flush: true, failOnError: true)
        //new Usda1(...).save(flush: true, failOnError: true)
        //Usda1 usda1 = new Usda1(...).save(flush: true, failOnError: true)
        //new Usda1(...).save(flush: true, failOnError: true)
        //new Usda1(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //usda1.id
    }

    void "test get"() {
        setupData()

        expect:
        usda1Service.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Usda1> usda1List = usda1Service.list(max: 2, offset: 2)

        then:
        usda1List.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        usda1Service.count() == 5
    }

    void "test delete"() {
        Long usda1Id = setupData()

        expect:
        usda1Service.count() == 5

        when:
        usda1Service.delete(usda1Id)
        sessionFactory.currentSession.flush()

        then:
        usda1Service.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Usda1 usda1 = new Usda1()
        usda1Service.save(usda1)

        then:
        usda1.id != null
    }
}
