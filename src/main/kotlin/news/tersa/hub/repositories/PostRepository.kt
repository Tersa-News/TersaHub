package news.tersa.hub.repositories

import news.tersa.hub.models.Post
import news.tersa.hub.models.User
import org.springframework.data.mongodb.repository.MongoRepository

interface PostRepository: MongoRepository<Post, String> {
    fun findByCreator(user: User): List<Post>
    fun findByContentContains(search: String): List<Post>
}