package news.tersa.hub.models

import jakarta.persistence.Id
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.DocumentReference
import java.util.*

@Document(collection = "posts")
class Post (

    @Id
    val id: String? = null,

    val content: String? = null,

    val title: String? = null,

    val shortDescription: String? = null,

    val regionSpecifier: String? = null,

    val categories: List<String>? = listOf(),

    @CreatedDate
    val created: Date? = null,

    @LastModifiedDate
    val modified: Date? = null,

    @CreatedBy
    @DocumentReference(lazy = true)
    val creator: User? = null

)