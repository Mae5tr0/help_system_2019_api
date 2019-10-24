package help_system_api.controller

import help_system_api.dto.ApiResponse
import help_system_api.dto.PagedResponse
import help_system_api.dto.user.ChangeRoleRequest
import help_system_api.dto.user.UserProfile
import help_system_api.dto.user.UserSummary
import help_system_api.security.CurrentUser
import help_system_api.security.UserPrincipal
import help_system_api.service.UserService
import help_system_api.utils.AppConstants
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/users")
class UserController(
        private val userService: UserService
) {
    @GetMapping("/user/profile")
    fun getCurrentUser(@CurrentUser currentUser: UserPrincipal): UserProfile {
        return UserProfile(currentUser.id, currentUser.email, currentUser.role)
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    fun getAllUsers(@CurrentUser currentUser: UserPrincipal,
                    @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) page: Int,
                    @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) size: Int
    ): PagedResponse<UserSummary> {
        val users = userService.getAllUsers(page, size)

        return PagedResponse(
                users.content.map { user -> UserSummary(email = user.email, role = user.role) },
                users.number,
                users.size,
                users.totalElements
        )
    }

    @PostMapping("/changeRole")
    @PreAuthorize("hasRole('ADMIN')")
    fun changeRole(@Valid @RequestBody changeRoleRequest: ChangeRoleRequest): ApiResponse {
        userService.changeRole(changeRoleRequest.userId, changeRoleRequest.newRole)

        return ApiResponse(success = true)
    }

}