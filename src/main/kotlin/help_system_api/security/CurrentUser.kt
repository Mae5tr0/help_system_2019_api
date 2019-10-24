package help_system_api.security

import org.springframework.security.core.annotation.AuthenticationPrincipal

//@Target(ElementType.PARAMETER, ElementType.TYPE)
//@Retention(RetentionPolicy.RUNTIME)
@AuthenticationPrincipal
annotation class CurrentUser