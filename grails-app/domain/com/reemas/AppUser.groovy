package com.reemas

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
@EqualsAndHashCode(includes='username')
@ToString(includes='username', includeNames=true, includePackage=false)
class AppUser implements Serializable {

    private static final long serialVersionUID = 1

    String username
    String password
    String email
    String userRealName
    boolean emailShow = false
    boolean enabled = true
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired

    Set<AppRole> getAuthorities() {
        (AppUserAppRole.findAllByAppUser(this) as List<AppUserAppRole>)*.appRole as Set<AppRole>
    }

    static constraints = {
        password nullable: false, blank: false, password: true
        username nullable: false, blank: false, unique: true
        email nullable: true
        userRealName nullable: true
        emailShow nullable: true
    }

    static mapping = {
	    password column: '`password`'
    }
}
