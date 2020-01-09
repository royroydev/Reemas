package com.reemas

import grails.gorm.services.Service

@Service(Usda1)
interface Usda1Service {

    Usda1 get(Serializable id)

    List<Usda1> list(Map args)

    Long count()

    void delete(Serializable id)

    Usda1 save(Usda1 usda1)

    Usda1 findByContainerIdAndActive(String containerId, boolean active)

}