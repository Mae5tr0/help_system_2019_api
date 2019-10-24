package help_system_api.dto.user

import help_system_api.model.RoleName

data class ChangeRoleRequest(
        val userId: Long,
        val newRole: RoleName
)