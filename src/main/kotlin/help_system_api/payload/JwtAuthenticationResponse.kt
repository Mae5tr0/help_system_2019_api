package help_system_api.payload

data class JwtAuthenticationResponse(
    val accessToken: String,
    val tokenType: String = "Bearer"
)