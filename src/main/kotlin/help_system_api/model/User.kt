package help_system_api.model

import org.hibernate.annotations.BatchSize
import org.hibernate.annotations.NaturalId
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Entity
@Table(name = "users")
class User(
        @Column(nullable = false, unique = true)
        @NaturalId
        @NotBlank
        @Size(max = 40)
        var email: String,

        @Column(nullable = false)
        @NotBlank
        @Size(max = 100)
        var password: String = "",

        @Enumerated(EnumType.STRING)
        @Column(length = 60)
        @NotBlank
        var role: RoleName = RoleName.CUSTOMER,

        @OneToMany(
                mappedBy = "user",
                cascade = [CascadeType.ALL],
                fetch = FetchType.LAZY,
                orphanRemoval = true
        )
        @BatchSize(size = 30)
        var tickets: Collection<Ticket> = arrayListOf()
) : DateAudit()