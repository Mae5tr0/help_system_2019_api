package help_system_api.dto.auth

data class SignInRequest(
    val email: String,
    val password: String
)