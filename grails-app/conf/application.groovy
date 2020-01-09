

// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'com.reemas.AppUser'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'com.reemas.AppUserAppRole'
grails.plugin.springsecurity.authority.className = 'com.reemas.AppRole'
grails.plugin.springsecurity.securityConfigType = "Annotation"

grails.plugin.springsecurity.controllerAnnotations.staticRules = [
	[pattern: '/',               access: ['permitAll']],
	[pattern: '/error',          access: ['permitAll']],
	[pattern: '/index',          access: ['permitAll']],
	[pattern: '/index.gsp',      access: ['permitAll']],
	[pattern: '/shutdown',       access: ['permitAll']],
	[pattern: '/assets/**',      access: ['permitAll']],
	[pattern: '/**/js/**',       access: ['permitAll']],
	[pattern: '/**/css/**',      access: ['permitAll']],
	[pattern: '/**/images/**',   access: ['permitAll']],
	[pattern: '/**/favicon.ico', access: ['permitAll']]

]

grails.plugin.springsecurity.filterChain.chainMap = [
	[pattern: '/assets/**',      filters: 'none'],
	[pattern: '/**/js/**',       filters: 'none'],
	[pattern: '/**/css/**',      filters: 'none'],
	[pattern: '/**/images/**',   filters: 'none'],
	[pattern: '/**/favicon.ico', filters: 'none'],
	[pattern: '/**',             filters: 'JOINED_FILTERS']
]

grails.plugin.springsecurity.logout.postOnly = false


environments {
	development {
		grails {
			mail {
				host = "lpmail03.lunariffic.com"
				port = 587
				username = "monitoring3@datapower-system.com"
				password = "kak1Buki+"
				props = ["mail.smtp.auth":"true",
						 "mail.smtp.port":"587"]
			}
		}

	}
	test {


	}
	production {
		grails {
			mail {
				host = "mail.datapower-system.com"
				port = 587
				username = "monitoring3@datapower-system.com"
				password = "kak1Buki+"
				props = ["mail.smtp.auth":"true",
						 "mail.smtp.port":"587"]
			}
		}
	}
}