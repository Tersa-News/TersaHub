package news.tersa.hub.models

import jakarta.persistence.Id
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.util.Date

@Document(collection = "users")
class User (

    @Id
    val id: String? = null,

    @Indexed(unique = true)
    val email: String? = null,

    @Indexed(unique = true)
    val username: String? = null,

    val password: String? = null,

    val location: String? = null,

    @CreatedDate
    val created: Date? = null,

    @LastModifiedDate
    val modified: Date? = null

)