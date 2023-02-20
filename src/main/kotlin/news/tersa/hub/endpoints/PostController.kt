package news.tersa.hub.endpoints

import news.tersa.hub.models.Post
import news.tersa.hub.repositories.PostRepository
import news.tersa.hub.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Endpoint for all post methods, such as:
 * - Creating
 * - Updating
 * - Deleting
 * - Querying
 */
@RestController
@RequestMapping("/api/posts")
class PostController {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var postRepository: PostRepository

    @PostMapping("/")
    fun createPost(): ResponseEntity<Post> {
        // TODO: Implement
        throw NotImplementedError()
    }

    @PatchMapping("/")
    fun editPost(): ResponseEntity<Post> {
        // TODO: Implement
        throw NotImplementedError()
    }

    @GetMapping("/{id}")
    fun getPost(@PathVariable id: String): ResponseEntity<Post> {
        // TODO: Implement
        throw NotImplementedError()
    }

    @GetMapping("/")
    fun getAllPosts(): ResponseEntity<List<Post>> {
        // TODO: Implement
        throw NotImplementedError()
    }

}