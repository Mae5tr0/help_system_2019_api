package help_system_api.dto.ticket

import help_system_api.model.StatusName
import help_system_api.dto.user.UserSummary

data class TicketResponse(
        val title: String,
        val status: StatusName,
        val content: String,
        val user: UserSummary
)