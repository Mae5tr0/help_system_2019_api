package help_system_api.dto.ticket

import help_system_api.dto.user.UserSummary
import help_system_api.model.StatusName

data class TicketListItemResponse(
        val title: String,
        val status: StatusName,
        val user: UserSummary
)