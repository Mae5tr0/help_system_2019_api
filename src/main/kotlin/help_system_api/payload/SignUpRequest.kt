package help_system_api.payload

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class SignUpRequest(
    @NotBlank
    @Size(max = 40)
    @Email
    val email: String,

    @NotBlank
    @Size(min = 6, max = 20)
    val password: String
)