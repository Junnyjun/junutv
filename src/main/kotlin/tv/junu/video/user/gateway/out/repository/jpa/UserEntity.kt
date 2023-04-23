package tv.junu.video.user.gateway.out.repository.jpa

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.IndexDirection
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import tv.junu.video.user.domain.Role
import tv.junu.video.user.domain.Role.ROLE_USER
import tv.junu.video.user.domain.User
import java.time.LocalDateTime

@Document(collection = "user")
class UserEntity(
    @Id val id: ObjectId = ObjectId.get(),
    @Indexed(unique = true) val email: String,
    val password: String,
    val name: String,
    val role: Role = ROLE_USER,
    @Indexed(direction = IndexDirection.DESCENDING) private val createdAt: LocalDateTime = LocalDateTime.now()
) {
    val toDomain: User
        get() = User(
            id = this.id,
            email = this.email,
            password = this.password,
            name = this.name,
            role = this.role,
        )

    companion object {
        fun fromDomain(user: User): UserEntity =
            UserEntity(
                id = user.id,
                email = user.email,
                password = user.password,
                name = user.name,
                role = user.role,
            )
    }
}