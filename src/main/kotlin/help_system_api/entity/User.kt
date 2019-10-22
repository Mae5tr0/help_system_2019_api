package help_system_api.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener::class)
//@JsonIgnoreProperties(
//        value = {"createdAt", "updatedAt"},
//        allowGetters = true
//)
class User(
    @Column(nullable = false, unique = true)
    @NotBlank
    @Size(max = 40)
    var email: String,

    @Column(nullable = false)
    @NotBlank
    @Size(max = 100)
    var password: String,

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "user_roles",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id"))
//    private roles: Set<Role> = HashSet<>(),

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    var createdAt: Date? = null,

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    var updatedAt: Date? = null
) : BaseEntity()