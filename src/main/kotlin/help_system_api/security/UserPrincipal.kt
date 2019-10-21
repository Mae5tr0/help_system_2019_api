package help_system_api.security

import com.fasterxml.jackson.annotation.JsonIgnore
import help_system_api.entity.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.GrantedAuthority
import java.util.stream.Collectors
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.util.*

class UserPrincipal(
        val id: Long?,

        @field:JsonIgnore
        val email: String,

        @field:JsonIgnore
        private val password: String,

        private val authorities: Collection<GrantedAuthority>
) : UserDetails {

    override fun getUsername(): String {
        return email
    }

    override fun getPassword(): String {
        return password
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return authorities
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
//            val authorities = user.getRoles().stream().map({ role -> SimpleGrantedAuthority(role.getName().name()) }
//            ).collect(Collectors.toList<T>())

            return UserPrincipal(
                    user.getId(),
                    user.email,
                    user.password,
                    listOf<GrantedAuthority>()
            )
        }
    }
}