package com.reemas

import grails.gorm.services.Service

@Service(ActO2)
interface ActO2Service {

    ActO2 get(Serializable id)

    List<ActO2> list(Map args)

    Long count()

    void delete(Serializable id)

    ActO2 save(ActO2 actO2)

    ActO2 findByContainerIdAndActive(String containerId, boolean active)

}