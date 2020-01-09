package com.reemas

class SupplyTemp {

    String containerId
    Double valueSupplyTemp
    Date creationDate = new Date()
    Date modificationDate = new Date()
    boolean active

    static constraints = {
        containerId unique: true, blank: false, nullable: false
        valueSupplyTemp nullable: false
        creationDate display: false
        modificationDate display: false
    }

    def beforeUpdate() {
        modificationDate = new Date()
    }


}
