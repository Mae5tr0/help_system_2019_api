package help_system_api.controller

import help_system_api.dto.ApiResponse
import org.springframework.http.ResponseEntity
import help_system_api.dto.auth.SignUpRequest
import org.springframework.web.bind.annotation.RequestBody
import javax.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import help_system_api.dto.auth.JwtAuthenticationResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import help_system_api.dto.auth.SignInRequest
import help_system_api.security.JwtTokenProvider
import help_system_api.repository.UserRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import help_system_api.dto.user.UserIdentityAvailability
import help_system_api.service.UserService
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
@RequestMapping("/api/auth")
class AuthController(
        private val userService: UserService,
        private val authenticationManager: AuthenticationManager,
        private val userRepository: UserRepository,
        private val tokenProvider: JwtTokenProvider
) {

    @PostMapping("/signin")
    fun authenticateUser(@Valid @RequestBody signInRequest: SignInRequest): ResponseEntity<*> {
        val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                        signInRequest.email,
                        signInRequest.password
                )
        )

        SecurityContextHolder.getContext().authentication = authentication

        val jwt = tokenProvider.generateToken(authentication)
        return ResponseEntity.ok(JwtAuthenticationResponse(jwt))
    }

    @PostMapping("/signup")
    fun registerUser(@Valid @RequestBody signUpRequest: SignUpRequest): ResponseEntity<ApiResponse> {
        val user = userService.createUser(email = signUpRequest.email, password = signUpRequest.password)

        val location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{id}")
                .buildAndExpand(user.id).toUri()

        return ResponseEntity.created(location).body(ApiResponse(true, "User registered successfully"))
    }

    @GetMapping("/checkEmailAvailability")
    fun checkEmailAvailability(@RequestParam(value = "email") email: String): UserIdentityAvailability {
        val isAvailable = !userRepository.existsByEmail(email)

        return UserIdentityAvailability(isAvailable)
    }
}