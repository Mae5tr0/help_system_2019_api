package help_system_api.security

import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.security.SignatureException
import java.util.*

@Component
class JwtTokenProvider {

    @Value("\${app.jwtSecret}")
    private val jwtSecret: String? = null

    @Value("\${app.jwtExpirationInMs}")
    private val jwtExpirationInMs: Int = 0

    fun generateToken(authentication: Authentication): String {
        val userPrincipal = authentication.principal as UserPrincipal
        val keyBytes = Decoders.BASE64.decode(jwtSecret)
        val key = Keys.hmacShaKeyFor(keyBytes)

        val now = Date()
        val expiryDate = Date(now.time + jwtExpirationInMs)

        return Jwts.builder()
                .setSubject(java.lang.Long.toString(userPrincipal.id))
                .setIssuedAt(Date())
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact()
    }

    fun getUserIdFromJWT(token: String): Long {
        val claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .body

        return java.lang.Long.parseLong(claims.subject)
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