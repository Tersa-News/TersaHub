package news.tersa.hub.endpoints.requesttypes

import java.util.*

data class QueryPostsRequest (
    val page: Int? = null,
    val pageSize: Int? = null,
    val regionQuery: String? = null,
    val creationDateStart: Date? = null,
    val creationDateEnd: Date? = null,
    val updatedDateStart: Date? = null,
    val updatedDateEnd: Date? = null,
    val authorUsername: String? = null,
    val categories: List<String>? = null
)