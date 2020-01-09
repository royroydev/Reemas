package reemas

import com.reemas.ActCO2
import com.reemas.ActO2
import com.reemas.AppRole
import com.reemas.AppUser
import com.reemas.AppUserAppRole
import com.reemas.ReeferData
import com.reemas.SetCO2
import com.reemas.SetO2
import com.reemas.SetPoint
import com.reemas.SupplyTemp
import com.reemas.Usda1
import com.reemas.Usda2
import com.reemas.Usda3
import grails.util.Environment

class BootStrap {

    def init = { servletContext ->

        println "Initializing..."
        if (Environment.current == Environment.DEVELOPMENT) {
            println "Development"
            // do something only in development
            def supplyTemp = new SupplyTemp(containerId: 'CGMU5339807', valueSupplyTemp: -1, creationDate: new Date(), active: true ).save()

            def setPoint = new SetPoint(containerId: 'CGMU5339807', valueSetPoint: 2, creationDate: new Date(), active: true ).save()

            def usda1 = new Usda1(containerId: 'CGMU5339807', valueUsda1: 0.90, creationDate: new Date(), active: true).save flush:true

            def usda2 = new Usda2(containerId: 'CGMU5339807', valueUsda2: 1.00, creationDate: new Date(), active: true).save flush:true

            def usda3 = new Usda3(containerId: 'CGMU5339807', valueUsda3: 1.30, creationDate: new Date(), active: true).save flush:true

            def setO2 = new SetO2(containerId: 'CGMU5339807', valueSetO2: 8.0, creationDate: new Date(), active: true).save flush:true

            def actO2 = new ActO2(containerId: 'CGMU5339807', valueActO2: 0, creationDate: new Date(), active: true).save flush:true

            def setCO2 = new SetCO2(containerId: 'CGMU5339807', valueSetCO2: 5.8, creationDate: new Date(), active: true).save flush:true

            def actCO2 = new ActCO2(containerId: 'CGMU5339807', valueActCO2: 5.8, creationDate: new Date(), active: true).save flush:true


            def roleUser = new AppRole(authority:"ROLE_USER").save(flush:true, failOnError:true)
            def roleAdmin = new AppRole(authority:"ROLE_ADMIN").save(flush:true, failOnError:true)
            def user = new AppUser(username:"user",password:"user", userRealName: 'Default User', email: "some@emailUser.com").save flush:true
            def admin = new AppUser(username:"admin",password:"admin", userRealName: 'Default Admin', email: "some@emailAdmin.com").save flush:true

            AppUserAppRole.create(user,roleUser).save(flush:true, failOnError:true)
            AppUserAppRole.create(admin,roleAdmin).save(flush:true, failOnError:true)

        } else if (Environment.current == Environment.TEST) {
            println "Test"
        } else if (Environment.current == Environment.PRODUCTION) {
            println "Production"
            def roleUser = AppRole.findByAuthority("ROLE_USER") ?: new AppRole(authority:"ROLE_USER").save(flush:true, failOnError:true)
            def roleAdmin = AppRole.findByAuthority("ROLE_ADMIN") ?: new AppRole(authority:"ROLE_ADMIN").save(flush:true, failOnError:true)
            def admin = AppUser.findByUsername("admin") ?: new AppUser(username:"admin",password:"admin1234.", userRealName: 'Default Admin', email: "some@emailAdmin.com").save(flush:true)
            def userRole = AppUserAppRole.findByAppUser(admin) ?: AppUserAppRole.create(admin,roleAdmin).save(flush:true, failOnError:true)
            println("End load user")
        }

    }
    def destroy = {
    }

}
