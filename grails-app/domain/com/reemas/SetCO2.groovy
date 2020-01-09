package com.reemas

class SetCO2 {

    String containerId
    Double valueSetCO2
    Date creationDate = new Date()
    Date modificationDate = new Date()
    boolean active

    static constraints = {
        containerId unique: true, blank: false, nullable: false
        valueSetCO2 nullable: false
        creationDate display: false
        modificationDate display: false
    }

    def beforeUpdate() {
        modificationDate = new Date()
    }
}
