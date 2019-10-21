package help_system_api.repository

import help_system_api.entity.User
import org.springframework.stereotype.Repository
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

@Repository
interface UserRepository: JpaRepository<User, Long> {
    fun findByEmail(email: String): Optional<User>

    fun findByIdIn(userIds: List<Long>): List<User>

    fun existsByEmail(email: String): Boolean
}
