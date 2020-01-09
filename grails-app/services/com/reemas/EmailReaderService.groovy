package com.reemas

import grails.core.GrailsApplication
import grails.gorm.transactions.Transactional
import grails.util.Environment

import javax.mail.*
import javax.mail.internet.MimeMultipart
import javax.mail.search.FlagTerm
import java.text.DateFormat
import java.text.SimpleDateFormat

@Transactional
class EmailReaderService {

    GrailsApplication grailsApplication
    ReeferDataService reeferDataService
    SupplyTempService supplyTempService
    SetPointService setPointService
    Usda1Service usda1Service
    Usda2Service usda2Service
    Usda3Service usda3Service
    SetO2Service setO2Service
    ActO2Service actO2Service
    SetCO2Service setCO2Service
    ActCO2Service actCO2Service


    def mailService

    /**
     * Reads Incoming Emails and Creates Db entries For Them
     * */
    void saveMailsToDB() {
        Folder folderInbox = null
        Folder folderArchive = null
        def folders = []
        try {
            folders = openMailFolder()
            folderInbox = folders[0]
            folderArchive = folders[1]

//            setPermissionToReadAndWrite(folderInbox)
//            setPermissionToReadAndWrite(folderArchive)
            folderInbox.open(Folder.READ_WRITE)
            folderArchive.open(Folder.READ_WRITE)


            List<Message> messages = getUnreadMails(folderInbox)

            saveMessages(messages, folderInbox, folderArchive)
        } catch (Exception e) {
            log.warn "${e.message}"
        } finally {
            folderInbox?.store?.close()
        }
    }

    List<ReeferData> saveMessages(List<Message> messages, Folder folderInbox, Folder archiveFolder) {
        List<ReeferData> mails = []
        println("mensajes")
        println(messages)
        for (msg in messages) {
            if (!msg.subject.startsWith(grailsApplication.config.getProperty('reemas.email.subjectPrefix'))) {
                log.info "incorrect message subject: " + msg.subject
                continue // break
            }

            if (msg.content instanceof MimeMultipart) {

                MimeMultipart part = (MimeMultipart) msg.content
                if (part.count < 2) {
                    // archive and delete message here
                    if (Environment.current == Environment.PRODUCTION) {
                        folderInbox.copyMessages(folderInbox.getMessages(msg.getMessageNumber(), msg.getMessageNumber()), archiveFolder)
                        msg.setFlag(Flags.Flag.DELETED, true)
                    }
                    if (Environment.current == Environment.DEVELOPMENT) {
                        folderInbox.copyMessages(folderInbox.getMessages(msg.getMessageNumber(), msg.getMessageNumber()), archiveFolder)
                        msg.setFlag(Flags.Flag.DELETED, true)
                    }
                    log.info "no attachment"
                    continue    //no attachment, next mail
                }

                def bodyStr = ""
                def c
                def latlongLine
                def latlongWords
                def emailTimeLine

                try {
                    def mailBody = part.getBodyPart(0)
                    InputStream bodyIS = mailBody.getInputStream()

                    while ((c = bodyIS.read()) != -1) bodyStr += (char)c

                    String[] bodyLines = bodyStr.split("\n")
                    for (bodyLine in bodyLines) {
                        if (bodyLine.startsWith("Time of Session (UTC): ")) {
                            emailTimeLine = bodyLine
                            continue
                        }
                        if (bodyLine.startsWith("Unit Location: ")) {
                            latlongLine = bodyLine
                            break
                        }
                    }
                    if (latlongLine != null) latlongWords = latlongLine.split(" ")
                    else
                        log.warn("Error occured when parsing email message body:\n" + bodyStr)
                } catch (Exception e) {
                    log.warn("Error occured when parsing email message body:\n" + bodyStr, e)
                }

                def attachment = part.getBodyPart(1)
                InputStream is = attachment.getInputStream()

                // main body info
                def reeferData = new ReeferData()
                reeferData.attachmentFilename = attachment.getFileName()
                reeferData.satelliteId = msg.subject.substring(19)
                if (latlongWords?.size() >= 8) {
                    reeferData.latitude = Double.parseDouble(latlongWords[4])
                    reeferData.longitude = Double.parseDouble(latlongWords[7])
                }
                reeferData.updateTime = new Date()
                def emailTimeString = emailTimeLine.substring(23)


                DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy", Locale.US)

//                DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy")
//                dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

                reeferData.emailTime =  dateFormat.parse(emailTimeString)
                // attachment
                def s = ""
                while ((c = is.read()) != -1) s += (char)c

                if ((int)s.charAt(0) != 0x02) {
                    log.info s
                    reeferData.remarks = s
                    if (!reeferData.save()) {
                        log.error "Failed to save reefer data. Error begins..."
                        reeferData.errors.each {
                            log.error it
                        }
                        log.error "Error ends..."
                    }
                    log.info "info email..ok"

                    if ( reeferData.remarks == "Reefer power off OR Serial com fail!") {
                        log.info "Sending power off alert"
                        try {
                            def listElement = emailAlert3.tokenize(",").size()
                            if (emailAlert3.tokenize(",").size() > 0) {
                                mailService.sendMail {
                                    to emailAlert3.tokenize(",")
                                    from serverUsername
                                    subject subjectEmailAlert3
                                    body "Satellite IMEI: " + reeferData.satelliteId + "\nReefer power off OR Serial com fail!"
                                }
                            }
                        } catch (Exception e) {
                            log.warn("Failed to send power off alert.", e)
                        }

                    }
                    // archive and delete message here
                    if (Environment.current == Environment.PRODUCTION) {
                        folderInbox.copyMessages(folderInbox.getMessages(msg.getMessageNumber(), msg.getMessageNumber()), archiveFolder)
                        msg.setFlag(Flags.Flag.DELETED, true)
                    }
                    // archive and delete message here
                    if (Environment.current == Environment.DEVELOPMENT) {
                        folderInbox.copyMessages(folderInbox.getMessages(msg.getMessageNumber(), msg.getMessageNumber()), archiveFolder)
                        msg.setFlag(Flags.Flag.DELETED, true)
                    }

                    continue //wrong format, not start with STX
                }



                String[] dataArray = s.split("\\|")
                if (dataArray?.size() == 13 || dataArray?.size() == 17) {

                    try {
                        reeferData.containerId = dataArray[2]
//                        log.info reeferData.containerId
                        switch((int)dataArray[1]) {
                            case 1:
                                reeferData.containerManufacturer = "Carrier"
                                break
                            case 2:
                                reeferData.containerManufacturer = "MHI"
                                break
                            case 3:
                                reeferData.containerManufacturer = "ThermoKing"
                                break
                            case 4:
                                reeferData.containerManufacturer = "Daikin"
                        }

                        reeferData.setTemperature = Double.parseDouble(dataArray[3])
                        reeferData.supplyTemperature = Double.parseDouble(dataArray[4])
                        reeferData.returnTemperature = Double.parseDouble(dataArray[5])

                        if (dataArray[6].trim().length() > 0 && dataArray[6] != "NA")
                            reeferData.setHumidity = Double.parseDouble(dataArray[6])
                        if (dataArray[7].trim().length() > 0 && dataArray[7] != "NA")
                            reeferData.humidity = Double.parseDouble(dataArray[7])
                        if (dataArray[8].trim().length() > 0 && dataArray[8] != "NA")
                            reeferData.usda1 = Double.parseDouble(dataArray[8])
                        if (dataArray[9].trim().length() > 0 && dataArray[9] != "NA")
                            reeferData.usda2 = Double.parseDouble(dataArray[9])
                        if (dataArray[10].trim().length() > 0 && dataArray[10] != "NA")
                            reeferData.usda3 = Double.parseDouble(dataArray[10])

                        DateFormat dateFormattakenTime = new SimpleDateFormat("dd/MM/yy;HH:mm")
                        dateFormattakenTime.setTimeZone(TimeZone.getTimeZone("UTC"));

                        if (dataArray?.size() == 17) {
                            reeferData.containerManufacturer = "StarCool"
                            if (dataArray[11].trim().length() > 0 && dataArray[11] != "NA")
                                reeferData.setO2 = Double.parseDouble(dataArray[11])
                            if (dataArray[12].trim().length() > 0 && dataArray[12] != "NA")
                                reeferData.actO2 = Double.parseDouble(dataArray[12])
                            if (dataArray[13].trim().length() > 0 && dataArray[13] != "NA")
                                reeferData.setCO2 = Double.parseDouble(dataArray[13])
                            if (dataArray[14].trim().length() > 0 && dataArray[14] != "NA")
                                reeferData.actCO2 = Double.parseDouble(dataArray[14])
                            //	reeferData.alarm = dataArray[15] //Mike added on 12/1/10	Mask alarm for Reefer alarm example P alarm
                            reeferData.takenTime = Date.parse("dd/MM/yy;HH:mm", dataArray[16].trim()) //Mike added on 12/1/10:
//                            reeferData.takenTime = dateFormattakenTime.parse(dataArray[16].trim()) //adnlos added on 29/03/19:
                        }else{
                            reeferData.containerManufacturer = "Carrier"
                            //	reeferData.alarm = dataArray[11] //Mike added on 12/1/10	Mask alarm for Reefer alarm example P alarm
                            reeferData.takenTime = Date.parse("dd/MM/yy;HH:mm", dataArray[12].trim()) //Mike added on 12/1/10:

//                            reeferData.takenTime =  dateFormattakenTime.parse(dataArray[12].trim()) //adnlos added on 29/03/19
                        }

                        if (Environment.current == Environment.PRODUCTION) {
                            if (reeferDataService.findByContainerIdAndTakenTime(reeferData.containerId, reeferData.takenTime) != null) {
                                log.warn "Duplicate records: " + reeferData.containerId + ", " + reeferData.takenTime + ", " + reeferData.attachmentFilename
                                // archive and delete message here
                                folderInbox.copyMessages(folderInbox.getMessages(msg.getMessageNumber(), msg.getMessageNumber()), archiveFolder)
                                msg.setFlag(Flags.Flag.DELETED, true)
                                continue
                            }
                        }

                        if (Environment.current == Environment.DEVELOPMENT) {
                            if (reeferDataService.findByContainerIdAndTakenTime(reeferData.containerId, reeferData.takenTime) != null) {
                                log.warn "Duplicate records: " + reeferData.containerId + ", " + reeferData.takenTime + ", " + reeferData.attachmentFilename
                                // archive and delete message here
                                folderInbox.copyMessages(folderInbox.getMessages(msg.getMessageNumber(), msg.getMessageNumber()), archiveFolder)
                                msg.setFlag(Flags.Flag.DELETED, true)
                                continue
                            }
                        }


                        if (!reeferDataService.save(reeferData)) {
                            log.error "Failed to save reefer data. Error begins..."
                            reeferData.errors.each {
                                log.error it
                            }
                            log.error "Error ends..."
                        }

                        if (reeferData.alarm != null && reeferData.alarm.length() > 0) {
                            log.info "Sending alarm: " + reeferData.alarm
                            try {
                                if (emailAlert2.tokenize(",").size() > 0) {
                                    mailService.sendMail {
                                        to emailAlert2.tokenize(",")
                                        from serverUsername
                                        subject subjectEmailAlert2
                                        body "Container: " + reeferData.containerId + "\nAlarm(s): " + reeferData.alarm + "\n" + reeferData.takenTime.format('yyyy-MM-dd HH:mm:ss')
                                    }
                                }
                            } catch (Exception e) {
                                log.warn("Failed to send alarm.", e)
                            }
                        }

                        // supply temp check
                        def supplyTempFind = supplyTempService.findByContainerIdAndActive(reeferData.containerId, true)
                        if (supplyTempFind) {
                            if (reeferData.supplyTemperature != null && reeferData.supplyTemperature >= supplyTempFind.valueSupplyTemp) {
                                log.info "Sending alarm for supply Temp: " + reeferData.supplyTemperature
                                try {
                                    if (emailAlert2.tokenize(",").size() > 0) {
                                        mailService.sendMail {
                                            to emailAlert2.tokenize(",")
                                            from serverUsername
                                            subject subjectEmailAlert2
                                            body "Container: " + reeferData.containerId + "\nSet Point: " + reeferData.setTemperature + "\nSupply Temp: " + reeferData.supplyTemperature + "\n" + reeferData.takenTime.format('yyyy-MM-dd HH:mm:ss')
                                        }
                                    }
                                } catch (Exception e) {
                                    log.warn("Failed to send alarm supplyTemp", e)
                                }
                            }
                        }

                        // set point check
                        def setPointFind = setPointService.findByContainerIdAndActive(reeferData.containerId, true)

                        if (setPointFind) {
                            if (reeferData.setTemperature != null && reeferData.setTemperature.doubleValue() != setPointFind.valueSetPoint) {
                                log.info "Sending alarm for set point: " + reeferData.setTemperature.doubleValue() + ", expected: " + setPointFind.valueSetPoint
                                try {
                                    if (emailAlert2.tokenize(",").size() > 0) {
                                        mailService.sendMail {
                                            to emailAlert2.tokenize(",")
                                            from serverUsername
                                            subject subjectEmailAlert2
                                            body "Container: " + reeferData.containerId + "\nSet Point Listing: " + setPointFind.valueSetPoint + "\nCurrent Set Point: " + reeferData.setTemperature.doubleValue() + "\n" + reeferData.takenTime.format('yyyy-MM-dd HH:mm:ss')

                                        }
                                    }
                                } catch (Exception e) {
                                    log.warn("Failed to send alarm setPoint", e)
                                }
                            }
                        }


                        // usda1 check
                        def usda1Find = usda1Service.findByContainerIdAndActive(reeferData.containerId, true)
                        if (usda1Find) {
                            if (reeferData.usda1 != null && reeferData.usda1.doubleValue() > usda1Find.valueUsda1) {
                                log.info "Sending alarm for usda 1: " + reeferData.usda1
                                try {
                                    if (emailAlert2.tokenize(",").size() > 0) {
                                        mailService.sendMail {
                                            to emailAlert2.tokenize(",")
                                            from serverUsername
                                            subject subjectEmailAlert2
                                            body "Container: " + reeferData.containerId + "\nUSDA1: " + reeferData.usda1 + "\n" + reeferData.takenTime.format('yyyy-MM-dd HH:mm:ss')
                                        }
                                    }

                                } catch (Exception e) {
                                    log.warn("Failed to send alarm Usda1.", e)
                                }
                            }
                        }

                        // usda2 check
                        def usda2Find = usda2Service.findByContainerIdAndActive(reeferData.containerId, true)
                        if (usda2Find) {
                            if (reeferData.usda2 != null && reeferData.usda2.doubleValue() > usda2Find.valueUsda2) {
                                log.info "Sending alarm for usda 2: " + reeferData.usda2
                                try {
                                    if (emailAlert2.tokenize(",").size() > 0) {
                                        mailService.sendMail {
                                            to emailAlert2.tokenize(",")
                                            from serverUsername
                                            subject subjectEmailAlert2
                                            body "Container: " + reeferData.containerId + "\nUSDA2: " + reeferData.usda2 + "\n" + reeferData.takenTime.format('yyyy-MM-dd HH:mm:ss')
                                        }
                                    }
                                } catch (Exception e) {
                                    log.warn("Failed to send alarm for usda2.", e)
                                }
                            }
                        }

                        // usda3 check
                        def usda3Find = usda3Service.findByContainerIdAndActive(reeferData.containerId, true)
                        if (usda3Find) {
                            if (reeferData.usda3 != null && reeferData.usda3.doubleValue() > usda3Find.valueUsda3) {
                                log.info "Sending alarm for usda 3: " + reeferData.usda3
                                try {
                                    if (emailAlert2.tokenize(",").size() > 0) {
                                        mailService.sendMail {
                                            to emailAlert2.tokenize(",")
                                            from serverUsername
                                            subject subjectEmailAlert2
                                            body "Container: " + reeferData.containerId + "\nUSDA3: " + reeferData.usda3 + "\n" + reeferData.takenTime.format('yyyy-MM-dd HH:mm:ss')
                                        }
                                    }
                                } catch (Exception e) {
                                    log.warn("Failed to send alarm Usda3.", e)
                                }
                            }
                        }


                        // set O2 check
                        def setO2Find = setO2Service.findByContainerIdAndActive(reeferData.containerId, true)
                        if (setO2Find) {
                            if (reeferData.setO2 != null && reeferData.setO2.doubleValue() != setO2Find.valueSetO2) {
                                log.info "Sending alarm for set O2: " + reeferData.setO2.doubleValue() + ", expected: " + setO2Find.valueSetO2
                                try {
                                    if (emailAlert2.tokenize(",").size() > 0) {
                                        mailService.sendMail {
                                            to emailAlert2.tokenize(",")
                                            from serverUsername
                                            subject subjectEmailAlert2
                                            body "Container: " + reeferData.containerId + "\nSet O2: " + reeferData.setO2+ "\n" + reeferData.takenTime.format('yyyy-MM-dd HH:mm:ss')
                                        }
                                    }
                                } catch (Exception e) {
                                    log.warn("Failed to send alarm SetO2", e)
                                }
                            }
                        }

                        // O2 check
                        def actO2Find = actO2Service.findByContainerIdAndActive(reeferData.containerId, true)
                        if (actO2Find) {
                            if (reeferData.actO2 != null && reeferData.actO2.doubleValue() > actO2Find.valueActO2) {
                                log.info "Sending alarm for act O2: " + reeferData.actO2
                                try {
                                    if (emailAlert2.tokenize(",").size() > 0) {
                                        mailService.sendMail {
                                            to emailAlert2.tokenize(",")
                                            from serverUsername
                                            subject subjectEmailAlert2
                                            body "Container: " + reeferData.containerId + "\nAct O2: " + reeferData.actO2 + "\n" + reeferData.takenTime.format('yyyy-MM-dd HH:mm:ss')
                                        }
                                    }
                                } catch (Exception e) {
                                    log.warn("Failed to send alarm ActO2", e)
                                }
                            }
                        }

                        // set CO2 check
                        def setCO2Find = setCO2Service.findByContainerIdAndActive(reeferData.containerId, true)
                        if (setCO2Find) {
                            if (reeferData.setCO2 != null && reeferData.setCO2.doubleValue() != setCO2Find.valueSetCO2) {
                                log.info "Sending alarm for set CO2: " + reeferData.setCO2.doubleValue() + ", expected: " + setCO2Find.valueSetCO2
                                try {
                                    if (emailAlert2.tokenize(",").size() > 0) {
                                        mailService.sendMail {
                                            to emailAlert2.tokenize(",")
                                            from serverUsername
                                            subject subjectEmailAlert2
                                            body "Container: " + reeferData.containerId + "\nSet CO2: " + reeferData.setCO2+ "\n" + reeferData.takenTime.format('yyyy-MM-dd HH:mm:ss')
                                        }
                                    }
                                } catch (Exception e) {
                                    log.warn("Failed to send alarm setCO2", e)
                                }
                            }
                        }

                        // CO2 check
                        def actCO2Find = actCO2Service.findByContainerIdAndActive(reeferData.containerId, true)
                        if (actCO2Find) {
                            if (reeferData.actCO2 != null && reeferData.actCO2.doubleValue() < actCO2Find.valueActCO2) {
                                log.info "Sending alarm for act CO2: " + reeferData.actCO2
                                try {
                                    if (emailAlert2.tokenize(",").size() > 0) {
                                        mailService.sendMail {
                                            to emailAlert2.tokenize(",")
                                            from serverUsername
                                            subject subjectEmailAlert2
                                            body "Container: " + reeferData.containerId + "\nAct CO2: " + reeferData.actCO2 + "\n" + reeferData.takenTime.format('yyyy-MM-dd HH:mm:ss')
                                        }
                                    }
                                } catch (Exception e) {
                                    log.warn("Failed to send alarm ActCO2", e)
                                }
                            }
                        }

                    }catch (Exception e){
                        log.info("Error occured when parsing email message: " + s, e)
                        continue
                    }

                    // archive and delete message here
                    if (Environment.current == Environment.PRODUCTION) {
                        folderInbox.copyMessages(folderInbox.getMessages(msg.getMessageNumber(), msg.getMessageNumber()), archiveFolder)
                        msg.setFlag(Flags.Flag.DELETED, true)
                    }
                    if (Environment.current == Environment.DEVELOPMENT) {
                        folderInbox.copyMessages(folderInbox.getMessages(msg.getMessageNumber(), msg.getMessageNumber()), archiveFolder)
                        msg.setFlag(Flags.Flag.DELETED, true)
                    }

                }else{

                    log.info "Wrong number of data fields"
                }
            }else{
                log.info "Not multipart mail"
            }
        }
        folderInbox.close(true)
        archiveFolder.close(true)
        folderInbox.store.close()
        return mails
    }

    /**
     * Logs Message Info, logging level set to Info
     * */
    void printMessagesInfo(Message message) {
        log.info "=============================================="
        log.info "From  ${message.from.toString()}"
        log.info "Recipients To ${message.getRecipients(Message.RecipientType.TO)}"
        log.info "Recipients CC ${message.getRecipients(Message.RecipientType.CC)}"
        log.info "Recipients BCC ${message.getRecipients(Message.RecipientType.BCC)}"
        log.info "Subject  ${message.subject}"
        log.info "=============================================="
    }

    List<Folder> openMailFolder() {
        Store imapStore = mailSession.getStore(mailProtocol)
        connectImapStore(imapStore, mailHost)
        //crear el folder si no existe
        Folder inboxFolder = imapStore.getFolder(mailFolderToRead)
        Folder archiveFolder = imapStore.getFolder(mailFolderArchiveToRead)

        def existFolderArchive =  existFolder(archiveFolder)

        def lisFolders = [inboxFolder, archiveFolder]
        return lisFolders
    }

    /**
     * Check if Exist Folder
     * */
    Folder existFolder(Folder folder){
        if (!folder.exists()) {
            folder.create(Folder.HOLDS_MESSAGES)
        }
        return folder
    }

    /**
     * Sets Read Write Permission on Mail Folder
     * */
    void setPermissionToReadAndWrite(Folder folder) {
        folder.open(Folder.READ_WRITE)
    }

    /**
     * Reads Unread Mails From Specied Id
     * */
    List<Message> getUnreadMails(Folder folder) {
        FlagTerm flagTerm = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
        List<Message> unread = folder.search(flagTerm);
        return unread
    }



    /**
     * Reads reads Mails From Specied Id to archive
     * */
    List<Message> getReadMailsToArchive(Folder folder) {
        FlagTerm flagTerm = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
        List<Message> unread = folder.search(flagTerm);
        return unread
    }

    private connectImapStore(Store imapStore, String host) {
        if (!imapStore.isConnected()) {
            imapStore.connect(host, emailToRead, emailsPassword)
        }
    }

    Session getMailSession() {
        return Session.getDefaultInstance(setDefaultMailProperties(), null)
    }

    /**
     * Sets Properties Used For Mail Session
     * */
    private setDefaultMailProperties() {
        Properties props = new Properties()
        props.setProperty("mail.store.protocol", mailProtocol)
        props.setProperty("mail.imap.host", mailHost)
        props.setProperty("mail.imap.port", mailPort)

        return props
    }

    /**
     * reads email from Config
     * */
    String getEmailToRead() {
        return grailsApplication.config.getProperty('reemas.email.server.username')
    }

    /**
     * reads password from Config
     * */
    String getEmailsPassword() {
        return grailsApplication.config.getProperty('reemas.email.server.password')
    }

    /**
     * reads folder name from Config
     * */
    String getMailFolderToRead() {
        return grailsApplication.config.getProperty('reemas.email.server.folderToRead')
    }

    /**
     * reads folder archive from Config
     * */
    String getMailFolderArchiveToRead() {
        return grailsApplication.config.getProperty('reemas.email.server.folderToArchive')
    }

    /**
     * reads protocol from Config
     * */

    String getMailProtocol() {
        return grailsApplication.config.getProperty('reemas.email.server.protocol')
    }

    /**
     * reads hostname from Config
     * */
    String getMailHost() {
        return grailsApplication.config.getProperty('reemas.email.server.host')
    }

    /**
     * reads port from Config
     * */
    String getMailPort() {
        return grailsApplication.config.getProperty('reemas.email.server.port')
    }


    /**
     * reads email alert2
     * */
    def getEmailAlert2() {
        return grailsApplication.config.getProperty('reemas.alert.email2.to')
    }

    /**
     * subject email alert 2
     * */
    String getSubjectEmailAlert2(){
        return grailsApplication.config.getProperty('reemas.alert.email2.subject')
    }

    /**
     * reads email alert 3
     * */
    def getEmailAlert3() {
        return grailsApplication.config.getProperty('reemas.alert.email3.to')
    }

    /**
     * subject email alert 3
     * */
    String getSubjectEmailAlert3(){
        return grailsApplication.config.getProperty('reemas.alert.email3.subject')
    }

    /**
     * email principal
     * */
    String getServerUsername(){
        return grailsApplication.config.getProperty('reemas.email.server.username')
    }

}
