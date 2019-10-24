package help_system_api.service

import help_system_api.entity.User
import help_system_api.exception.ResourceAlreadyExists
import help_system_api.exception.ResourceNotFoundException
import help_system_api.model.RoleName
import help_system_api.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
        private val userRepository: UserRepository,
        private val passwordEncoder: PasswordEncoder
) {

    fun changeRole(userId: Long, role: RoleName): User {
        val user = userRepository.findById(userId).orElseThrow { ResourceNotFoundException("User", "id", userId) }
        user.role = role
        userRepository.save(user)

        return user
    }

    fun createUser(email: String, password: String): User {
        if (userRepository.existsByEmail(email)) {
            throw ResourceAlreadyExists("User", "email", email)
        }

        val user = User(email = email)
        user.password = passwordEncoder.encode(password)

        return userRepository.save(user)
    }

    fun getAllUsers(page: Int, size: Int): Page<User> {
        val pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt")

        return userRepository.findAll(pageable)
    }
}