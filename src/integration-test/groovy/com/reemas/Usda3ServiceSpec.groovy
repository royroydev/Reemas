package com.reemas

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class Usda3ServiceSpec extends Specification {

    Usda3Service usda3Service
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Usda3(...).save(flush: true, failOnError: true)
        //new Usda3(...).save(flush: true, failOnError: true)
        //Usda3 usda3 = new Usda3(...).save(flush: true, failOnError: true)
        //new Usda3(...).save(flush: true, failOnError: true)
        //new Usda3(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //usda3.id
    }

    void "test get"() {
        setupData()

        expect:
        usda3Service.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Usda3> usda3List = usda3Service.list(max: 2, offset: 2)

        then:
        usda3List.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        usda3Service.count() == 5
    }

    void "test delete"() {
        Long usda3Id = setupData()

        expect:
        usda3Service.count() == 5

        when:
        usda3Service.delete(usda3Id)
        sessionFactory.currentSession.flush()

        then:
        usda3Service.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Usda3 usda3 = new Usda3()
        usda3Service.save(usda3)

        then:
        usda3.id != null
    }
}
