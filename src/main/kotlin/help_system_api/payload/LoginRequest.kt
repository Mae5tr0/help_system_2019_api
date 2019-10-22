package help_system_api.payload

data class LoginRequest(
    val email: String,
    val password: String
)