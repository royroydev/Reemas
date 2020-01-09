package com.reemas

class ActO2 {

    String containerId
    Double valueActO2
    Date creationDate = new Date()
    Date modificationDate = new Date()
    boolean active

    static constraints = {
        containerId unique: true, blank: false, nullable: false
        valueActO2 nullable: false
        creationDate display: false
        modificationDate display: false
    }

    def beforeUpdate() {
        modificationDate = new Date()
    }
}
