package news.tersa.hub.endpoints.requesttypes

/**
 * Body of a request for a JWT
 */
data class JwtRequest(
    val username: String,
    val password: String
)