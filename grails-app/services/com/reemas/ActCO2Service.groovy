package com.reemas

import grails.gorm.services.Service

@Service(ActCO2)
interface ActCO2Service {

    ActCO2 get(Serializable id)

    List<ActCO2> list(Map args)

    Long count()

    void delete(Serializable id)

    ActCO2 save(ActCO2 actCO2)

    ActCO2 findByContainerIdAndActive(String containerId, boolean active)

}