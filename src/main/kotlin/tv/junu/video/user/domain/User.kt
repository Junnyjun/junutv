package tv.junu.video.user.domain

import org.bson.types.ObjectId
import tv.junu.video.user.domain.Role.ROLE_USER

class User(
    val id: ObjectId,
    val email: String,
    val password: String,
    val name: String,
    val role: Role
) {
    constructor(
        email: String,
        password: String,
        name: String,
    ) : this(ObjectId.get(), email, password, name, ROLE_USER)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}