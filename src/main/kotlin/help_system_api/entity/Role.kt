package help_system_api.entity

import help_system_api.model.RoleName
import org.hibernate.annotations.NaturalId
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Entity
@Table(name = "roles")
class Role(
        @Enumerated(EnumType.STRING)
        @NaturalId
        @Column(length = 60)
        var name: RoleName
): BaseEntity()