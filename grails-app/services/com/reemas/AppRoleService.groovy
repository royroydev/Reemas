package com.reemas

import grails.gorm.services.Service

@Service(AppRole)
interface AppRoleService {

    AppRole get(Serializable id)

    List<AppRole> list(Map args)

    Long count()

    void delete(Serializable id)

    AppRole save(AppRole appRole)

}