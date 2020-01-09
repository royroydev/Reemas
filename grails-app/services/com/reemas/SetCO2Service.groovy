package com.reemas

import grails.gorm.services.Service

@Service(SetCO2)
interface SetCO2Service {

    SetCO2 get(Serializable id)

    List<SetCO2> list(Map args)

    Long count()

    void delete(Serializable id)

    SetCO2 save(SetCO2 setCO2)

    SetCO2 findByContainerIdAndActive(String containerId, boolean active)

}