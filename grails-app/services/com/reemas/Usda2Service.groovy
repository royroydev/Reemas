package com.reemas

import grails.gorm.services.Service

@Service(Usda2)
interface Usda2Service {

    Usda2 get(Serializable id)

    List<Usda2> list(Map args)

    Long count()

    void delete(Serializable id)

    Usda2 save(Usda2 usda2)

    Usda2 findByContainerIdAndActive(String containerId, boolean active)

}