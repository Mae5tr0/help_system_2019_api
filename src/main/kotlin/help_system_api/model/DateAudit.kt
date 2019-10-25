package help_system_api.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*
import javax.persistence.*

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
@JsonIgnoreProperties(
        value = ["createdAt", "updatedAt"],
        allowGetters = true
)
abstract class DateAudit(
        @Column(nullable = false, updatable = false)
        @Temporal(TemporalType.TIMESTAMP)
        @CreatedDate
        var createdAt: Date? = null,

        @Column(nullable = false)
        @Temporal(TemporalType.TIMESTAMP)
        @LastModifiedDate
        var updatedAt: Date? = null
) : BaseEntity()