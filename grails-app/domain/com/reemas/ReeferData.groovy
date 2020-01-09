package com.reemas

class ReeferData {
    String containerId
    String containerManufacturer
    String satelliteId
    String attachmentFilename
    Double setTemperature
    Double supplyTemperature
    Double returnTemperature
    Double setHumidity
    Double humidity
    Double usda1
    Double usda2
    Double usda3
    Double setO2
    Double actO2
    Double setCO2
    Double actCO2
    Double cargo
    String alarm
    Double latitude
    Double longitude
    Date takenTime
    String remarks
    Date emailTime
    Date updateTime

    static constraints = {
        containerId(nullable:true )
        containerManufacturer(nullable:true)
        satelliteId(nullable:true)
        attachmentFilename(nullable:true)
        setTemperature(nullable:true)
        supplyTemperature(nullable:true)
        returnTemperature(nullable:true)
        setHumidity(nullable:true)
        humidity(nullable:true)
        usda1(nullable:true)
        usda2(nullable:true)
        usda3(nullable:true)
        setO2(nullable:true)
        actO2(nullable:true)
        setCO2(nullable:true)
        actCO2(nullable:true)
        cargo(nullable:true)
        alarm(nullable:true, blank: true)
        latitude(nullable:true)
        longitude(nullable:true)
        takenTime(nullable:true)
        remarks(nullable:true)
        emailTime()
        updateTime()
    }
}
