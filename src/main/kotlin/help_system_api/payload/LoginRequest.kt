package help_system_api.payload

import javax.validation.constraints.NotBlank

data class LoginRequest(
    @NotBlank
    val usernameOrEmail: String? = null,

    @NotBlank
    val password: String? = null
)