package help_system_api.dto

data class ApiResponse(
        val success: Boolean,
        val message: String? = ""
)