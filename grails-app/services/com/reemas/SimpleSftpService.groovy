package com.reemas

import com.jcraft.jsch.Channel
import com.jcraft.jsch.ChannelSftp
import com.jcraft.jsch.JSch
import com.jcraft.jsch.Session
import com.jcraft.jsch.SftpATTRS
import grails.gorm.transactions.Transactional

@Transactional
class SimpleSftpService {
    def grailsApplication
    def mailService

    def uploadFile(InputStream inputStream, String fileName) {
        connect { ChannelSftp sftp ->
            sftp.put inputStream, fileName
        }
    }

    def uploadPathFile(InputStream inputStream, String dirName, String fileName) {
        def result = connect { ChannelSftp sftp ->
            SftpATTRS attrs = null
            String currentDirectory = sftp.pwd()
            try {
                attrs = sftp.stat(currentDirectory+"/"+dirName)
            } catch (Exception e) {
                log.warn(" not found folder " + dirName)
            }

            if (attrs == null) {
                sftp.mkdir(dirName)
            }

            try {
                sftp.cd(dirName)
                sftp.put inputStream, fileName
            } catch (Exception e){
                log.error e
                result = false

                log.error("Failed to upload SFTP.", e)
                try {
                    if (emailAlert1.tokenize(",").size() > 0) {
                        mailService.sendMail {
                            to emailAlert1.tokenize(",")
                            from serverUsername
                            subject subjectEmailAlert1
                            body "Error occurs during SFTP uploading."
                        }
                    }
                } catch (Exception e1) {
                    log.warn("Failed to send alert for sftp upload.", e1)
                }
            }
        }

        return result
    }

    def downloadFile(String fileName) {
        connect({ ChannelSftp sftp ->
            File outputFile = File.createTempFile(fileName,'')
            outputFile?.newOutputStream() << sftp.get(fileName)
            outputFile
        }, false)
    }

    def removeFile(String fileName) throws Throwable {
        connect { ChannelSftp sftp ->
            sftp.rm fileName

        }
    }

    def renameFile(String oldPath, String newPath, String fileName) {
        def result = connect { ChannelSftp sftp ->
            String currentDirectory = sftp.pwd()
            SftpATTRS attrs = null

            try {
                attrs = sftp.stat(currentDirectory+"/"+newPath)
            } catch (Exception e) {
                log.warn("not found folder")
            }

            if (attrs == null){
                sftp.mkdir(newPath)
            }

            try {
                sftp.rename(oldPath + fileName, newPath + fileName)
            } catch (Exception e1){
                result = false
                log.error "Failed to move " + fileName + " to dest folder. " + e1
                try {
                    if (emailAlert1.tokenize(",").size() > 0) {
                        mailService.sendMail {
                            to emailAlert1.tokenize(",")
                            from serverUsername
                            subject subjectEmailAlert1
                            body "Failed to move " + f.getName() + " from work folder to inbound folder."
                        }
                    }
                } catch (Exception e) {
                    log.error("Failed to send alert for ftp upload.", e)
                }
            }
        }

        return result
    }

    def existDir(String dirName){
        connect { ChannelSftp sftp ->
            SftpATTRS attrs = null
            String currentDirectory = sftp.pwd()
            try {
                attrs = sftp.stat(currentDirectory+"/"+dirName)
            } catch (Exception e) {
                System.out.println(" not found")
            }

            if (attrs != null) {
                System.out.println("Directory exists IsDir="+attrs.isDir())
            } else {
                System.out.println("Creating dir " + dirName)
                attrs.mkdir(dirName)
            }
        }
    }

    def isConected(){
        def result = true
        connect { ChannelSftp sftp ->
            SftpATTRS attrs = null
            String currentDirectory = sftp.pwd()
            try {
                attrs = sftp.stat(currentDirectory+"/"+dirName)
            } catch (Exception e) {
                System.out.println(" not found")
                result = false

            }

            if (attrs != null) {
                System.out.println("Directory exists IsDir="+attrs.isDir())
            } else {
                System.out.println("Creating dir " + dirName)
                attrs.mkdir(dirName)
            }
        }
        return result
    }

    def createDir(String dirName) {
        connect { ChannelSftp sftp ->
            sftp.mkdir dirName
        }
    }

    private def connect(Closure c, boolean disconnect = true) {
        Session session = null
        ChannelSftp sftp = null
        def result = true
        String server =  grailsApplication.config.getProperty('reemas.sftp.remote.host')
        String username = grailsApplication.config.reemas.sftp.remote.username
        String password = grailsApplication.config.reemas.sftp.remote.password
        String remoteDir = grailsApplication.config.reemas.sftp.remote.remoteDir
        Integer port = grailsApplication.config.reemas.sftp.remote.port.toInteger()
        String keyFilePath = grailsApplication.config.reemas.sftp.remote.keyFilePath
        Boolean throwException = grailsApplication.config.reemas.sftp.remote.throwException

        try {
            JSch jSch = new JSch()
            session = jSch.getSession username, server, port
            session.setConfig "StrictHostKeyChecking", "no"

            if (password) {
                session.password = password
            } else {
                File keyFile = new File(keyFilePath)
                jSch.addIdentity(keyFile?.absolutePath)
            }

            session.connect()
            Channel channel = session.openChannel "sftp"
            channel.connect()
            sftp = channel as ChannelSftp
            sftp.cd remoteDir
            c.call sftp
        } catch (Exception e) {
            result = false
            log.error "Failed to login SFTP Server: " + e

            try {

                if (emailAlert1.tokenize(",").size() > 0) {
                    mailService.sendMail {
                        to emailAlert1.tokenize(",")
                        from serverUsername
                        subject subjectEmailAlert1
                        body "Failed to login SFTP Server"
                    }
                }
            } catch (Exception em) {
                result = false
                log.error("Failed to send alert for ftp upload.", em)
            }

            if (throwException) {
                throw e
            }

        } finally {
            if (disconnect) {
                sftp?.exit()
                session?.disconnect()
            }
        }

        return result
    }

    /**
     * reads email alert 1
     * */
    def getEmailAlert1() {
        return grailsApplication.config.getProperty('reemas.alert.email1.to')
    }

    /**
     * subject email alert 1
     * */
    String getSubjectEmailAlert1(){
        return grailsApplication.config.getProperty('reemas.alert.email1.subject')
    }

    /**
     * email principal
     * */
    String getServerUsername(){
        return grailsApplication.config.getProperty('reemas.email.server.username')
    }

}
