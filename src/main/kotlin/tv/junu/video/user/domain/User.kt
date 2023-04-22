package tv.junu.video.user.domain

import org.bson.types.ObjectId

class User(
    val id: ObjectId,
    val email: String,
    val password: String,
    val name: String,
    val role: Role
) {
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