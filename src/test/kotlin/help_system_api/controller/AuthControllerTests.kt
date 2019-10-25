package help_system_api.controller

import help_system_api.repository.UserRepository
import help_system_api.service.UserService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.util.AssertionErrors.assertTrue
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional


@ExtendWith(SpringExtension::class)
@SpringBootTest
@TestPropertySource("/test.properties")
@AutoConfigureMockMvc
@Transactional
class AuthControllerTests {

    @Autowired
    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var userService: UserService

    @Test
    fun signUp() {
        mvc.perform(
                post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test@test.com\",\"password\":\"password\"}")
        )
                .andExpect(status().isCreated)

        val user = userRepository.findByEmail("test@test.com")

        assertTrue("User created successfully", user.isPresent())
    }

    @Test
    fun signIn() {
        userService.createUser(email = "another@email.com", password = "super_password")

        mvc.perform(
                post("/api/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"another@email.com\",\"password\":\"super_password\"}")
        )
                .andExpect(status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.tokenType").value("Bearer"))
    }
}
