package com.reemas

import grails.gorm.services.Service

@Service(SetO2)
interface SetO2Service {

    SetO2 get(Serializable id)

    List<SetO2> list(Map args)

    Long count()

    void delete(Serializable id)

    SetO2 save(SetO2 setO2)

    SetO2 findByContainerIdAndActive(String containerId, boolean active)

}