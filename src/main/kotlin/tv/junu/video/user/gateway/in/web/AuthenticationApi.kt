package tv.junu.video.user.gateway.`in`.web

import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import tv.junu.video.user.TokenCreate
import tv.junu.video.user.UserDetailsService
import tv.junu.video.user.domain.Role.*
import tv.junu.video.user.gateway.`in`.web.exception.PasswordNotMatchException
import tv.junu.video.user.gateway.out.repository.UserRepository
import tv.junu.video.user.gateway.out.repository.jpa.UserEntity

@RestController
class AuthenticationApi(
    private val tokenCreate: TokenCreate,
    private val passwordEncoder: PasswordEncoder,
    private val userDetailsService: UserDetailsService,
    private val userRepository: UserRepository
) {
    @PostMapping("/api/v1/login")
    fun customLogin(@RequestBody authRequest: AuthRequest): Mono<ResponseEntity<AuthResponse>> =
        userDetailsService.loadUserByUsername(authRequest.email)
            .filter { passwordEncoder.matches(authRequest.password, it.password) }
            .map { ResponseEntity.ok(AuthResponse(tokenCreate.createToken(it.id, it.role))) }
            .switchIfEmpty(Mono.error(PasswordNotMatchException()))

    @PostMapping("/api/v1/signup")
    fun customSignup(@RequestBody authRequest: AuthRequest): Mono<ResponseEntity<AuthResponse>> =
        userDetailsService.loadUserByUsername(authRequest.email)
            .filter { passwordEncoder.matches(authRequest.password, it.password) }
            .map { ResponseEntity.ok(AuthResponse(tokenCreate.createToken(it.id, it.role))) }

    @PostMapping("/api/v1/regist")
    fun customRegist(@RequestBody authRequest: AuthRequest): Mono<ResponseEntity<AuthResponse>> = userRepository.save(
        UserEntity(
            name = authRequest.name,
            email = authRequest.email,
            password = passwordEncoder.encode(authRequest.password),
        )
    )
        .map { ResponseEntity.ok(AuthResponse(tokenCreate.createToken(it.id, it.role))) }

}

data class AuthResponse(
    val token: String
)

data class AuthRequest(
    val name: String,
    val email: String,
    val password: String
)