package help_system_api.dto.auth

data class JwtAuthenticationResponse(
        val accessToken: String,
        val tokenType: String = "Bearer"
)