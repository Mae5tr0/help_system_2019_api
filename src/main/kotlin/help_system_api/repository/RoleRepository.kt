package help_system_api.repository

import help_system_api.entity.Role
import help_system_api.model.RoleName
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RoleRepository : JpaRepository<Role, Long> {
//    fun findByName(roleName: RoleName): Optional<Role>
}