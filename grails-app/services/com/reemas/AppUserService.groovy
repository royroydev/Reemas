package com.reemas

import grails.gorm.services.Service

@Service(AppUser)
interface AppUserService {

    AppUser get(Serializable id)

    List<AppUser> list(Map args)

    Long count()

    void delete(Serializable id)

    AppUser save(AppUser appUser)

}