package help_system_api.security

import org.springframework.security.core.annotation.AuthenticationPrincipal
import java.lang.annotation.Documented
import java.lang.annotation.ElementType
import java.lang.annotation.RetentionPolicy


//@Target(ElementType.PARAMETER, ElementType.TYPE)
//@Retention(RetentionPolicy.RUNTIME)
@Documented
@AuthenticationPrincipal
annotation class CurrentUser