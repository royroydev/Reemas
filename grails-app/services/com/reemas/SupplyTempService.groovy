package com.reemas

import grails.gorm.services.Service

@Service(SupplyTemp)
interface SupplyTempService {

    SupplyTemp get(Serializable id)

    List<SupplyTemp> list(Map args)

    Long count()

    void delete(Serializable id)

    SupplyTemp save(SupplyTemp supplyTemp)

    SupplyTemp findByContainerIdAndActive(String containerId, boolean active)

}