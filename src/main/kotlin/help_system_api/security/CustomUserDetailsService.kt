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
class CustomUserDetailsService(
    val userRepository: UserRepository
) : UserDetailsService {

    @Transactional
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(email: String): UserDetails {
        val user = userRepository.findByEmail(email)
                .orElseThrow({ UsernameNotFoundException("User not found with email : $email") })

        return UserPrincipal.create(user)
    }

    @Transactional
    fun loadUserById(id: Long): UserDetails {
        val user = userRepository.findById(id).orElseThrow<RuntimeException> { ResourceNotFoundException("User", "id", id) }

        return UserPrincipal.create(user)
    }
}