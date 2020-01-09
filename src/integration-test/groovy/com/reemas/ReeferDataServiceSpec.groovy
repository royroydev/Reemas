package com.reemas

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class ReeferDataServiceSpec extends Specification {

    ReeferDataService reeferDataService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new ReeferData(...).save(flush: true, failOnError: true)
        //new ReeferData(...).save(flush: true, failOnError: true)
        //ReeferData reeferData = new ReeferData(...).save(flush: true, failOnError: true)
        //new ReeferData(...).save(flush: true, failOnError: true)
        //new ReeferData(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //reeferData.id
    }

    void "test get"() {
        setupData()

        expect:
        reeferDataService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<ReeferData> reeferDataList = reeferDataService.list(max: 2, offset: 2)

        then:
        reeferDataList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        reeferDataService.count() == 5
    }

    void "test delete"() {
        Long reeferDataId = setupData()

        expect:
        reeferDataService.count() == 5

        when:
        reeferDataService.delete(reeferDataId)
        sessionFactory.currentSession.flush()

        then:
        reeferDataService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        ReeferData reeferData = new ReeferData()
        reeferDataService.save(reeferData)

        then:
        reeferData.id != null
    }
}
