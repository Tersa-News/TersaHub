package news.tersa.hub.config.security

import news.tersa.hub.repositories.UserRepository
import news.tersa.hub.models.User as TersaUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class JwtUserDetailsService : UserDetailsService {

    @Autowired
    private lateinit var userRepo: UserRepository

    @Autowired
    private lateinit var bcryptEncoder: PasswordEncoder

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepo.findByUsername(username)
            ?: throw UsernameNotFoundException("User not found with username: $username")
        return User(user.username, user.password, listOf())
    }

    @Transactional
    fun save(username: String, password: String, email: String): TersaUser? {

        val newUser = TersaUser(
            username = username,
            password = bcryptEncoder.encode(password),
            email = email
        )
        return userRepo.save(newUser)
    }

}