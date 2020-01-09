package com.reemas

import grails.core.GrailsApplication

class UploadFTPFileJob {

    GrailsApplication grailsApplication
    def mailService
    SimpleSftpService simpleSftpService
    def delimiter = "|"
    long lastJobIndex

    static triggers = {
        cron name: 'ftpTrigger', cronExpression: "0 35 * * * ?"
//        simple name: 'mySimpleTriggerftp', startDelay: 120000, repeatInterval: 120000 //Only dev
    }

    def execute() {
        log.info "Start Job Cron Sftp Upload"
        Date now = new Date()
        def reemasRecords = []

        def lastIndexFile = new File(localWorkDir + nameFileIndex)
        if (lastIndexFile.exists()) {
            lastIndexFile.eachLine { line ->
                if (line.length() > 0) lastJobIndex = Long.parseLong(line)
            }
        }

        log.info "Read lastJobIndex: " + lastJobIndex // review this point from version one

        if (lastJobIndex == null || lastJobIndex == 0) {
            def startTime
            use(groovy.time.TimeCategory) {
                startTime = now - 24.hour
            }
            reemasRecords = ReeferData.findAllByTakenTimeGreaterThan(startTime)
        } else {
            reemasRecords = ReeferData.findAllByIdGreaterThan(lastJobIndex)
        }


        log.info "reemasRecords: " + reemasRecords

        if (reemasRecords.size() > 0){

            new File(localWorkDir + localActiveDir + "reemas_" + now.format('yyyyMMddHHmmss') + ".txt").withWriter { out ->
                for (recordInstance in reemasRecords) {
                    def tempString = recordInstance.id
                    if (recordInstance.containerId == null || recordInstance.containerId.length() == 0) continue
                    //tempString += (delimiter + recordInstance.containerId)
                    tempString += (delimiter + recordInstance.containerId.substring(0,10))

                    if (recordInstance.setTemperature >= -40.0 && recordInstance.setTemperature <= 50.0)
                        tempString += (delimiter + recordInstance.setTemperature)
                    else
                        tempString += (delimiter + "99")

                    if (recordInstance.supplyTemperature >= -42.0 && recordInstance.supplyTemperature <= 50.0)
                        tempString += (delimiter + recordInstance.supplyTemperature)
                    else
                        tempString += (delimiter + "99")

                    if (recordInstance.returnTemperature >= -42.0 && recordInstance.returnTemperature <= 50.0)
                        tempString += (delimiter + recordInstance.returnTemperature)
                    else
                        tempString += (delimiter + "99")

                    tempString += (delimiter + recordInstance.humidity?:"")

                    if (recordInstance.usda1 == null)
                        tempString += (delimiter + "")
                    else if (recordInstance.usda1 >= -42.0 && recordInstance.usda1 <= 50.0)
                        tempString += (delimiter + recordInstance.usda1)
                    else
                        tempString += (delimiter + "99")

                    if (recordInstance.usda2 == null)
                        tempString += (delimiter + "")
                    else if (recordInstance.usda2 >= -42.0 && recordInstance.usda2 <= 50.0)
                        tempString += (delimiter + recordInstance.usda2)
                    else
                        tempString += (delimiter + "99")

                    if (recordInstance.usda3 == null)
                        tempString += (delimiter + "")
                    else if (recordInstance.usda3 >= -42.0 && recordInstance.usda3 <= 50.0)
                        tempString += (delimiter + recordInstance.usda3)
                    else
                        tempString += (delimiter + "99")
                    if (recordInstance.setO2 == null)
                        tempString += (delimiter + "")
                    else if (recordInstance.setO2 >= -42.0 && recordInstance.setO2 <= 50.0)
                        tempString += (delimiter + recordInstance.setO2)
                    else
                        tempString += (delimiter + "99")

                    if (recordInstance.actO2 == null)
                        tempString += (delimiter + "")
                    else if (recordInstance.actO2 >= -42.0 && recordInstance.actO2 <= 50.0)
                        tempString += (delimiter + recordInstance.actO2)
                    else
                        tempString += (delimiter + "99")
                    if (recordInstance.setCO2 == null)
                        tempString += (delimiter + "")
                    else if (recordInstance.setCO2 >= -42.0 && recordInstance.setCO2 <= 50.0)
                        tempString += (delimiter + recordInstance.setCO2)
                    else
                        tempString += (delimiter + "99")

                    if (recordInstance.actCO2 == null)
                        tempString += (delimiter + "")
                    else if (recordInstance.actCO2 >= -42.0 && recordInstance.actCO2 <= 50.0)
                        tempString += (delimiter + recordInstance.actCO2)
                    else
                        tempString += (delimiter + "99")

                    tempString += (delimiter + recordInstance.alarm?:"")

                    tempString += (delimiter + recordInstance.takenTime.format('yyyyMMddHHmmss'))
                    out.writeLine(tempString)
                }
            }

            File activeFolder = new File(localWorkDir + localActiveDir )
            for (f in activeFolder.listFiles()) {

                if (!f.getName().startsWith("reemas_")) continue
                InputStream ins = new FileInputStream(f);

                def storeStatus = simpleSftpService.uploadPathFile(ins, remoteTempfolder, f.getName())

                if (storeStatus){
                    def renameStatus = simpleSftpService.renameFile(remoteTempfolder, remoteDestfolder, f.getName())
                    if (renameStatus){
                        log.info "SimpleSftpService Success!!"
                    }
                    f.renameTo(new File(localWorkDir  + localArchiveDir + f.getName()))
                }else{
                    break
                    log.error "something bad server is down"
                }
                ins.close()
            }

        }else {
            log.error "There are no records"
        }

    }

    /**
     * get dir Local
     * */
    String getLocalWorkDir(){
        return grailsApplication.config.getProperty('reemas.sftp.local.localDir')
    }

    /**
     * archive dir for local
     * */
    String getLocalArchiveDir(){
        return grailsApplication.config.getProperty('reemas.sftp.local.dataDirArchive')
    }

    /**
     * get active dir for local
     * */
    String getLocalActiveDir(){
        return grailsApplication.config.getProperty('reemas.sftp.local.dataDirActive')
    }


    /**
     * get NameFile dir for sftp
     * */
    String getNameFileIndex(){
        return  grailsApplication.config.getProperty('reemas.sftp.local.dataNameFile')
    }

    /**
     * get dir Remote sftp
     * */
    String getRemoteWorkDir(){
        return grailsApplication.config.getProperty('reemas.sftp.remote.remoteDir')
    }

    /**
     * get Temp Folder dir for sftp
     * */
    String getRemoteTempfolder(){
        return  grailsApplication.config.getProperty('reemas.sftp.remote.tempfolder')
    }

    /**
     * get DestFolder dir for sftp
     * */
    String getRemoteDestfolder(){
        return  grailsApplication.config.getProperty('reemas.sftp.remote.destfolder')
    }
}
