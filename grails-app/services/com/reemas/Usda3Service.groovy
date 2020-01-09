package com.reemas

import grails.gorm.services.Service

@Service(Usda3)
interface Usda3Service {

    Usda3 get(Serializable id)

    List<Usda3> list(Map args)

    Long count()

    void delete(Serializable id)

    Usda3 save(Usda3 usda3)

    Usda3 findByContainerIdAndActive(String containerId, boolean active)

}