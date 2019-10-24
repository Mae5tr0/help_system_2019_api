package help_system_api.dto

data class PagedResponse<T>(
        val data: List<T>,
        val page: Int,
        val size: Int,
        val total: Long
)

