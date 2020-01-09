package com.reemas

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class Usda2ServiceSpec extends Specification {

    Usda2Service usda2Service
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Usda2(...).save(flush: true, failOnError: true)
        //new Usda2(...).save(flush: true, failOnError: true)
        //Usda2 usda2 = new Usda2(...).save(flush: true, failOnError: true)
        //new Usda2(...).save(flush: true, failOnError: true)
        //new Usda2(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //usda2.id
    }

    void "test get"() {
        setupData()

        expect:
        usda2Service.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Usda2> usda2List = usda2Service.list(max: 2, offset: 2)

        then:
        usda2List.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        usda2Service.count() == 5
    }

    void "test delete"() {
        Long usda2Id = setupData()

        expect:
        usda2Service.count() == 5

        when:
        usda2Service.delete(usda2Id)
        sessionFactory.currentSession.flush()

        then:
        usda2Service.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Usda2 usda2 = new Usda2()
        usda2Service.save(usda2)

        then:
        usda2.id != null
    }
}
