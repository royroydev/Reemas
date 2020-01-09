package com.reemas

class SetPoint {

    String containerId
    Double valueSetPoint
    Date creationDate = new Date()
    Date modificationDate = new Date()
    boolean active

    static constraints = {
        containerId unique: true, blank: false, nullable: false
        valueSetPoint nullable: false
        creationDate display: false
        modificationDate display: false
    }

    def beforeUpdate() {
        modificationDate = new Date()
    }

}
