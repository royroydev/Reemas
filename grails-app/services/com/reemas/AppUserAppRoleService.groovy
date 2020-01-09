package com.reemas

import grails.gorm.services.Service

@Service(AppUserAppRole)
interface AppUserAppRoleService {

    AppUserAppRole get(Serializable id)

    List<AppUserAppRole> list(Map args)

    Long count()

    void delete(Serializable id)

    AppUserAppRole save(AppUserAppRole appUserAppRole)

    AppUserAppRole findByAppUser(AppUser appUser)


}