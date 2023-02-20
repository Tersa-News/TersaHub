package news.tersa.hub.config.security

import news.tersa.hub.models.User
import news.tersa.hub.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User as SpringUser
import org.springframework.stereotype.Service

@Service
class RequestUserUtils {

    @Autowired
    private lateinit var userRepo: UserRepository

    /**
     * Returns the currently authenticated User's basic information, such as username.
     * This does not make any calls to the database
     */
    fun getAuthedUser(): SpringUser {
        return SecurityContextHolder.getContext().authentication.principal as SpringUser
    }

    /**
     * Returns the currently authenticated user's detailed information.
     * This makes a call to the database.
     */
    fun getAuthedUserFromDB(): User {
        val user = SecurityContextHolder.getContext().authentication.principal as User
        return userRepo.findByUsername(user.username!!) ?: throw Exception("User account error - please try again")
    }
}