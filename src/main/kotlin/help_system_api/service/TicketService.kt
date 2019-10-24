package help_system_api.service

import help_system_api.entity.Ticket
import help_system_api.exception.ResourceNotFoundException
import help_system_api.model.RoleName
import help_system_api.model.StatusName
import help_system_api.repository.TicketRepository
import org.springframework.stereotype.Service
import help_system_api.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort

@Service
class TicketService(
        private val ticketRepository: TicketRepository,
        private val userRepository: UserRepository
) {
    fun createTicket(userId: Long, title: String, content: String): Ticket {
        val user = userRepository.findById(userId).orElseThrow { ResourceNotFoundException("User", "id", userId) }

        val ticket = Ticket(
            title = title,
            content = content,
            user = user
        )

        return ticketRepository.save(ticket)
    }

    // TODO add validation for current user
    fun getTicketById(userId: Long, ticketId: Long): Ticket {
        return ticketRepository.findById(ticketId).orElseThrow { ResourceNotFoundException("Ticket", "id", ticketId) }
    }

    fun changeStatus(userId: Long, ticketId: Long, newStatus: StatusName): Ticket {
        val ticket = ticketRepository.findById(ticketId).orElseThrow { ResourceNotFoundException("Ticket", "id", ticketId) }
        ticket.status = newStatus
        ticketRepository.save(ticket)

        return ticket
    }

    fun getAllTickets(userId: Long, page: Int, size: Int): Page<Ticket> {
        val user = userRepository.findById(userId).orElseThrow { ResourceNotFoundException("User", "id", userId) }
        val pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt")

        return if (user.role == RoleName.CUSTOMER) {
            ticketRepository.findByUserId(user.id!!, pageable)
        } else {
            ticketRepository.findAllWithUsers(pageable)
        }
    }
}