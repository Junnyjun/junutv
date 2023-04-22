package tv.junu.video.user.gateway.out.jpa

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.IndexDirection
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import tv.junu.video.user.domain.Role
import tv.junu.video.user.domain.Role.ROLE_USER
import java.time.LocalDateTime

@Document(collection = "user")
class UserEntity(
    @Id val id: ObjectId = ObjectId.get(),
    @Indexed(unique = true) val email: String,
    val password: String,
    val name: String,
    val role: Role,
    @Indexed(direction = IndexDirection.DESCENDING) private val createdAt: LocalDateTime = LocalDateTime.now()
) {
    constructor(email: String, password: String) : this(email = email, password = password, name = "mangjoo", role = ROLE_USER)
}