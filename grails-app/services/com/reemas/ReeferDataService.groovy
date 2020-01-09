package com.reemas

import grails.gorm.services.Service

@Service(ReeferData)
interface ReeferDataService {

    ReeferData get(Serializable id)

    List<ReeferData> list(Map args)

    Long count()

    void delete(Serializable id)

    ReeferData save(ReeferData reeferData)

    ReeferData findByContainerIdAndTakenTime(String containerId, Date takenTime)


}