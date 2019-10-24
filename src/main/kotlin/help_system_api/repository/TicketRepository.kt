package help_system_api.repository

import help_system_api.entity.Ticket
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface TicketRepository : JpaRepository<Ticket, Long> {
    fun findByUserId(userId: Long, pageable: Pageable): Page<Ticket>

    @Query("SELECT t FROM Ticket t JOIN t.user")
    fun findAllWithUsers(pageable: Pageable): Page<Ticket>
}
