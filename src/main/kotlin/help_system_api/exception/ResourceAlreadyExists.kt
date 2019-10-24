package help_system_api.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class ResourceAlreadyExists(
        private val resourceName: String,
        private val fieldName: String,
        private val fieldValue: Any
) : RuntimeException(String.format("%s already exists %s : '%s'", resourceName, fieldName, fieldValue))