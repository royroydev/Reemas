package com.reemas

import grails.gorm.services.Service

@Service(SetPoint)
interface SetPointService {

    SetPoint get(Serializable id)

    List<SetPoint> list(Map args)

    Long count()

    void delete(Serializable id)

    SetPoint save(SetPoint setPoint)

    SetPoint findByContainerIdAndActive(String containerId, boolean active)

}