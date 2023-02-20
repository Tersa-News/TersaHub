package news.tersa.hub.repositories

import news.tersa.hub.models.User
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository: MongoRepository<User, String> {
    fun findByEmail(email: String): User?
    fun findByUsername(username: String): User?
}