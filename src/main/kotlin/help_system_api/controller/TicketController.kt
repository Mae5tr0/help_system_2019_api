package help_system_api.controller

import help_system_api.dto.ApiResponse
import help_system_api.dto.PagedResponse
import help_system_api.dto.ticket.ChangeTicketStatusRequest
import help_system_api.dto.ticket.CreateTicketRequest
import help_system_api.dto.ticket.TicketListItemResponse
import help_system_api.dto.ticket.TicketResponse
import help_system_api.dto.user.UserSummary
import help_system_api.security.CurrentUser
import help_system_api.security.UserPrincipal
import help_system_api.service.TicketService
import help_system_api.utils.AppConstants
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import javax.validation.Valid

@RestController
@RequestMapping("/api/tickets")
class TicketController(
        private val ticketService: TicketService

) {

    @GetMapping("/{pollId}")
    fun getTicketById(
            @CurrentUser currentUser: UserPrincipal,
            @PathVariable ticketId: Long
    ): TicketResponse {
        val ticket = ticketService.getTicketById(currentUser.id, ticketId)
        val user = ticket.user

        return TicketResponse(
                title = ticket.title,
                status = ticket.status,
                content = ticket.content!!,
                user = UserSummary(user.email, user.role)
        )
    }

    @GetMapping
    fun getAllTickets(
            @CurrentUser currentUser: UserPrincipal,
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) page: Int,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) size: Int
    ): PagedResponse<TicketListItemResponse> {
        val tickets = ticketService.getAllTickets(userId = currentUser.id, page = page, size = size)

        return PagedResponse(
                tickets.content.map { ticket ->
                    TicketListItemResponse(
                            title = ticket.title,
                            status = ticket.status,
                            user = UserSummary(
                                    email = ticket.user.email,
                                    role = ticket.user.role
                            )
                    )
                },
                tickets.number,
                tickets.size,
                tickets.totalElements
        )
    }

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    fun createTicket(
            @CurrentUser currentUser: UserPrincipal,
            @Valid @RequestBody createTicketRequest: CreateTicketRequest
    ): ResponseEntity<*> {
        val ticket = ticketService.createTicket(
                userId = currentUser.id,
                title = createTicketRequest.title,
                content = createTicketRequest.content
        )

        val location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{ticketId}")
                .buildAndExpand(ticket.id).toUri()

        return ResponseEntity.created(location)
                .body(ApiResponse(true, "Ticket Created Successfully"))
    }

    @PostMapping("/changeStatus")
    @PreAuthorize("hasRole('MANAGER')")
    fun changeStatus(
            @CurrentUser currentUser: UserPrincipal,
            @Valid @RequestBody changeTicketStatusRequest: ChangeTicketStatusRequest
    ): ApiResponse {
        ticketService.changeStatus(
                userId = currentUser.id,
                ticketId = changeTicketStatusRequest.ticketId,
                newStatus = changeTicketStatusRequest.newStatus
        )

        return ApiResponse(success = true)
    }
}