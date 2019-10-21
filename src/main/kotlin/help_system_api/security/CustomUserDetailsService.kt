package help_system_api.security

import help_system_api.exception.ResourceNotFoundException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import help_system_api.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class CustomUserDetailsService : UserDetailsService {

    @Autowired
    internal var userRepository: UserRepository? = null

    @Transactional
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(email: String): UserDetails {
        // Let people login with either username or email
        val user = userRepository!!.findByEmail(email)
                .orElseThrow({ UsernameNotFoundException("User not found with email : $email") })

        return UserPrincipal.create(user)
    }

    @Transactional
    fun loadUserById(id: Long): UserDetails {
        val user = userRepository!!.findById(id).orElseThrow<RuntimeException> { ResourceNotFoundException("User", "id", id) }

        return UserPrincipal.create(user)
    }
}