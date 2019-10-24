package help_system_api.dto.auth

import javax.validation.constraints.Email
import javax.validation.constraints.Size

data class SignUpRequest(
    @Size(max = 40)
    @Email
    val email: String,

    @Size(min = 6, max = 20)
    val password: String
)