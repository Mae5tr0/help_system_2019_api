package help_system_api.dto.ticket

import help_system_api.model.StatusName

data class ChangeTicketStatusRequest(
        val ticketId: Long,
        val newStatus: StatusName
)