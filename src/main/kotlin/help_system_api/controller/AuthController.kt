package help_system_api.controller

import help_system_api.payload.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import help_system_api.exception.AppException
import help_system_api.model.RoleName
import ch.qos.logback.core.joran.spi.ConsoleTarget.findByName
import help_system_api.entity.User
import org.apache.tomcat.jni.SSL.setPassword
import org.springframework.http.HttpStatus
import help_system_api.payload.SignUpRequest
import org.springframework.web.bind.annotation.RequestBody
import javax.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import help_system_api.payload.JwtAuthenticationResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.postgresql.gss.MakeGSS.authenticate
import help_system_api.payload.LoginRequest
import help_system_api.security.JwtTokenProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import help_system_api.repository.RoleRepository
import help_system_api.repository.UserRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authenticationManager: AuthenticationManager,
    private val userRepository: UserRepository,
//    private val roleRepository: RoleRepository,
    private val passwordEncoder: PasswordEncoder,
    private val tokenProvider: JwtTokenProvider
) {

    @PostMapping("/signin")
    fun authenticateUser(@Valid @RequestBody loginRequest: LoginRequest): ResponseEntity<*> {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                loginRequest.email,
                loginRequest.password
            )
        )

        SecurityContextHolder.getContext().authentication = authentication

        val jwt = tokenProvider.generateToken(authentication)
        return ResponseEntity.ok(JwtAuthenticationResponse(jwt))
    }

    @PostMapping("/signup")
    fun registerUser(@Valid @RequestBody signUpRequest: SignUpRequest): ResponseEntity<*> {
        if (userRepository.existsByEmail(signUpRequest.email)) {
            return ResponseEntity(ApiResponse(false, "Email Address already in use!"),
                HttpStatus.BAD_REQUEST)
        }

        // Creating user's account
        val user = User(email = signUpRequest.email, password = "")
        user.password = passwordEncoder.encode(signUpRequest.password)

//        val userRole = roleRepository!!.findByName(RoleName.ROLE_USER)
//                .orElseThrow { AppException("User Role not set.") }

//        user.setRoles(Collections.singleton(userRole))

        val result = userRepository.save(user)

        val location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{id}")
                .buildAndExpand(result.id).toUri()

        return ResponseEntity.created(location).body(ApiResponse(true, "User registered successfully"))
    }
}