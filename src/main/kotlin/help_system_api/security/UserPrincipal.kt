package help_system_api.security

import help_system_api.model.User
import help_system_api.model.RoleName
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

class UserPrincipal(
        val id: Long,
        val email: String,
        val role: RoleName,
        private val password: String
) : UserDetails {
    override fun getUsername(): String {
        return email
    }

    override fun getPassword(): String {
        return password
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return listOf(SimpleGrantedAuthority("ROLE_$role"))
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as UserPrincipal?
        return Objects.equals(id, that!!.id)
    }

    override fun hashCode(): Int {
        return Objects.hash(id)
    }

    companion object {
        fun create(user: User): UserPrincipal {
            return UserPrincipal(
                    user.id!!,
                    user.email,
                    user.role,
                    user.password
            )
        }
    }
}