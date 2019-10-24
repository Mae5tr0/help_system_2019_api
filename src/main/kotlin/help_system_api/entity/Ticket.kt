package help_system_api.entity

import help_system_api.model.StatusName
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Entity
@Table(name = "tickets")
class Ticket(
        @Column(length = 60)
        @Size(max = 60)
        @NotBlank
        var title: String = "",

        @NotBlank
        var content: String? = "",

        @Enumerated(EnumType.STRING)
        @Column(length = 60)
        @NotBlank
        var status: StatusName = StatusName.OPEN,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id", nullable = false)
        var user: User
) : DateAudit()