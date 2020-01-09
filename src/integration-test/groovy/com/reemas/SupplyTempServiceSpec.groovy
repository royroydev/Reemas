package com.reemas

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class SupplyTempServiceSpec extends Specification {

    SupplyTempService supplyTempService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new SupplyTemp(...).save(flush: true, failOnError: true)
        //new SupplyTemp(...).save(flush: true, failOnError: true)
        //SupplyTemp supplyTemp = new SupplyTemp(...).save(flush: true, failOnError: true)
        //new SupplyTemp(...).save(flush: true, failOnError: true)
        //new SupplyTemp(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //supplyTemp.id
    }

    void "test get"() {
        setupData()

        expect:
        supplyTempService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<SupplyTemp> supplyTempList = supplyTempService.list(max: 2, offset: 2)

        then:
        supplyTempList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        supplyTempService.count() == 5
    }

    void "test delete"() {
        Long supplyTempId = setupData()

        expect:
        supplyTempService.count() == 5

        when:
        supplyTempService.delete(supplyTempId)
        sessionFactory.currentSession.flush()

        then:
        supplyTempService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        SupplyTemp supplyTemp = new SupplyTemp()
        supplyTempService.save(supplyTemp)

        then:
        supplyTemp.id != null
    }
}
