package help_system_api.security

import org.springframework.security.core.AuthenticationException
import javax.servlet.http.HttpServletResponse
import javax.servlet.ServletException
import java.io.IOException
import javax.servlet.http.HttpServletRequest
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class JwtAuthenticationEntryPoint : AuthenticationEntryPoint {
    @Throws(IOException::class, ServletException::class)
    override fun commence(httpServletRequest: HttpServletRequest,
                 httpServletResponse: HttpServletResponse,
                 e: AuthenticationException) {
//        logger.error("Responding with unauthorized error. Message - {}", e.getMessage())
        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorised")//e.getMessage())
    }

//    companion object {
//        private val logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint::class.java)
//    }
}