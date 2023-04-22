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


@Component
class TokenCreate(
        private val jwtEncoder: JwtEncoder,
        @Value("\${jwt.token.time}")
        private val time: Long,
) {
    private val now = Instant.now()

    fun createToken(userId: ObjectId, role: Role): String = JwtClaimsSet.builder()
            .issuer("junutv")
            .issuedAt(Instant.now())
            .expiresAt(now.plusSeconds(time))
            .subject(userId.toString())
            .claims { it["role"] = role.name }
            .build()
            .let { JwtEncoderParameters.from(it) }
            .let { jwtEncoder.encode(it).tokenValue }
}