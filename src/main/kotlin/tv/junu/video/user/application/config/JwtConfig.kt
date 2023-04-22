package tv.junu.video.user.application.config

import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey.Builder
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.proc.SecurityContext
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.jwt.*
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey

@Configuration
class JwtConfig {
    @Bean
    fun jwtEncoder(
        @Value("\${jwt.token.private}") privateKey: RSAPrivateKey,
        @Value("\${jwt.token.public}") publicKey: RSAPublicKey,
    ): JwtEncoder = Builder(publicKey).privateKey(privateKey).build()
        .let { ImmutableJWKSet<SecurityContext>(JWKSet(it)) }
        .let { NimbusJwtEncoder(it) }

    @Bean
    fun jwtDecoder(
        @Value("\${jwt.token.public}") publicKey: RSAPublicKey,
    ): ReactiveJwtDecoder = NimbusReactiveJwtDecoder(publicKey)
}