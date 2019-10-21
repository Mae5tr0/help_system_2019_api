package help_system_api.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Entity
//@Table(name = "users", uniqueConstraints = {
//    @UniqueConstraint(columnNames = {
//        "username"
//    }),
//    @UniqueConstraint(columnNames = {
//        "email"
//    })
//})
@Table(name = "users")
//@EntityListeners(AuditingEntityListener.class)
//@JsonIgnoreProperties(
//        value = {"createdAt", "updatedAt"},
//        allowGetters = true
//)
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @Column(nullable = false, unique = true)
    @NotBlank
    @Size(max = 40)
    val email: String,

    @Column(nullable = false)
    @NotBlank
    @Size(max = 100)
    val password: String,

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "user_roles",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id"))
//    private roles: Set<Role> = HashSet<>(),

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    val createdAt: Date,

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    val updatedAt: Date
)