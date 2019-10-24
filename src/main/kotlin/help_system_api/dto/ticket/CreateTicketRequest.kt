package help_system_api.dto.ticket

data class CreateTicketRequest(
    val title: String,
    val content: String
)
