package help_system_api.dto.user

import help_system_api.model.RoleName

data class UserProfile(
        val id: Long,
        val email: String,
        val role: RoleName
)