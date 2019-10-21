package help_system_api.security

import java.security.SignatureException
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*
import io.jsonwebtoken.*
import kotlin.reflect.jvm.internal.impl.utils.CollectionsKt.compact


@Component
class JwtTokenProvider {

    @Value("\${app.jwtSecret}")
    private val jwtSecret: String? = null

    @Value("\${app.jwtExpirationInMs}")
    private val jwtExpirationInMs: Int = 0

    fun generateToken(authentication: Authentication): String {

        val userPrincipal = authentication.getPrincipal() as UserPrincipal

        val now = Date()
        val expiryDate = Date(now.getTime() + jwtExpirationInMs)



        return Jwts.builder()
                .setSubject(java.lang.Long.toString(userPrincipal.id!!))
                .setIssuedAt(Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact()
    }

    fun getUserIdFromJWT(token: String): Long {
        val claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()

        return java.lang.Long.parseLong(claims.getSubject())
    }

    fun validateToken(authToken: String): Boolean {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken)
            return true
        } catch (ex: SignatureException) {
//            logger.error("Invalid JWT signature")
        } catch (ex: MalformedJwtException) {
//            logger.error("Invalid JWT token")
        } catch (ex: ExpiredJwtException) {
//            logger.error("Expired JWT token")
        } catch (ex: UnsupportedJwtException) {
//            logger.error("Unsupported JWT token")
        } catch (ex: IllegalArgumentException) {
//            logger.error("JWT claims string is empty.")
        }

        return false
    }

//    companion object {
//
//        private val logger = LoggerFactory.getLogger(JwtTokenProvider::class.java)
//    }
}