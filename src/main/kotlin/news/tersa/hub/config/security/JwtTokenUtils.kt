package news.tersa.hub.config.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import java.util.Date
import javax.crypto.SecretKey
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component

/**
 * Defines how to generate and validate JWT tokens
 */
@Component
class JwtTokenUtils(@Value("\${jwt.secret}") secret: String) {

    companion object {
        const val JWT_TOKEN_VALIDITY = (10000 * 60 * 60).toLong()
    }

    private lateinit var key: SecretKey

    init {
        key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secret))
    }

    /**
     * Retrieve the username from the JWT Token
     */
    fun getUsernameFromToken(token: String): String {
        return getClaimFromToken(token, Claims::getSubject)
    }

    // retrieve expiration date from jwt token
    fun getExpirationDateFromToken(token: String): Date {
        return getClaimFromToken(token, Claims::getExpiration)
    }

    fun <T> getClaimFromToken(token: String, claimsResolver: (Claims) -> T): T {
        return claimsResolver(getAllClaimsFromToken(token))
    }

    private fun getAllClaimsFromToken(token: String): Claims {
        return try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body
        } catch (ex: Exception) {
            Jwts.claims()
        }
    }

    private fun isTokenExpired(token: String): Boolean {
        val expiration = getExpirationDateFromToken(token)
        return expiration.before(Date())
    }

    fun generateToken(userDetails: UserDetails): String {
        val claims: Map<String, Any> = HashMap()
        return doGenerateToken(claims, userDetails.username)
    }

    private fun doGenerateToken(claims: Map<String, Any?>, subject: String): String {
        Keys.secretKeyFor(SignatureAlgorithm.HS512)
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
            .signWith(key).compact()
    }

    fun validateToken(token: String, userDetails: UserDetails): Boolean {
        val username = getUsernameFromToken(token)
        return username == userDetails.username && !isTokenExpired(token)
    }

}