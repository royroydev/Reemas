import com.reemas.AppUserPasswordEncoderListener
import org.springframework.web.servlet.i18n.SessionLocaleResolver

// Place your Spring DSL code here
beans = {
    appUserPasswordEncoderListener(AppUserPasswordEncoderListener)

    localeResolver(SessionLocaleResolver) {
        defaultLocale= new java.util.Locale('en');
    }
}
