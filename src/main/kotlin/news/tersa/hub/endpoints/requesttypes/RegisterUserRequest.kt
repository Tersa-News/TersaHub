package news.tersa.hub.endpoints.requesttypes

/**
 * Defines the body for a request to create a new user
 */
data class RegisterUserRequest(
    val username: String,
    val password: String,
    val email: String
)