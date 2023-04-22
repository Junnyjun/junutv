package tv.junu.video.user

import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.stereotype.Component
import tv.junu.video.user.domain.Role
import java.nio.charset.StandardCharsets.*
import java.time.Instant

fun interface TokenCreate {
    fun createToken(request: TokenCreateRequest): String

    @Component
    class TokenCreateUsecase(
        private val jwtEncoder: JwtEncoder,
        @Value("\${jwt.token.time}")
        private val time: Long,
    ) : TokenCreate {
        private val now = Instant.now()

        override fun createToken(request:TokenCreateRequest): String = JwtClaimsSet.builder()
            .issuer("junutv")
            .issuedAt(Instant.now())
            .expiresAt(now.plusSeconds(time))
            .subject(request.userString)
            .claims { it["role"] = request.roleString }
            .build()
            .let { JwtEncoderParameters.from(it) }
            .let { jwtEncoder.encode(it).tokenValue }
    }

    data class TokenCreateRequest(
        val userId: ObjectId,
        val role: Role,
    ){
       val userString = userId.toString()
       val roleString = role.name
    }
}