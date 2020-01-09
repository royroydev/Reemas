package com.reemas

class ActCO2 {

    String containerId
    Double valueActCO2
    Date creationDate = new Date()
    Date modificationDate = new Date()
    boolean active

    static constraints = {
        containerId unique: true, blank: false, nullable: false
        valueActCO2 nullable: false
        creationDate display: false
        modificationDate display: false
    }

    def beforeUpdate() {
        modificationDate = new Date()
    }


}
