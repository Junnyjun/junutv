package tv.junu.video.user.gateway.`in`.web

import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import tv.junu.video.user.FindUser
import tv.junu.video.user.TokenCreate
import tv.junu.video.user.TokenCreate.TokenCreateRequest
import tv.junu.video.user.domain.Role.*
import tv.junu.video.user.exception.PasswordNotMatchException
import tv.junu.video.user.gateway.out.repository.UserRepository
import tv.junu.video.user.gateway.out.repository.jpa.UserEntity

sealed interface AuthenticationApi {
    fun login(authRequest: AuthRequest): Mono<ResponseEntity<AuthResponse>>
    fun signup(authRequest: AuthRequest): Mono<ResponseEntity<AuthResponse>>
    fun signin(authRequest: AuthRequest): Mono<ResponseEntity<AuthResponse>>

    @RestController
    class AuthenticationWepApi(
        private val tokenCreate: TokenCreate,
        private val passwordEncoder: PasswordEncoder,
        private val findUser: FindUser,
        private val userRepository: UserRepository
    ) : AuthenticationApi {
        @PostMapping("/api/v1/login")
        override fun login(@RequestBody authRequest: AuthRequest): Mono<ResponseEntity<AuthResponse>> =
            findUser.ByEmail(authRequest.email)
                .filter { passwordEncoder.matches(authRequest.password, it.password) }
                .map { tokenCreate.createToken(TokenCreateRequest(it.id, it.role)) }
                .map { ResponseEntity.ok(AuthResponse(it)) }
                .switchIfEmpty(Mono.error(PasswordNotMatchException()))

        @PostMapping("/api/v1/signup")
        override fun signup(@RequestBody authRequest: AuthRequest): Mono<ResponseEntity<AuthResponse>> =
            findUser.ByEmail(authRequest.email)
                .filter { passwordEncoder.matches(authRequest.password, it.password) }
                .map { tokenCreate.createToken(TokenCreateRequest(it.id, it.role)) }
                .map { ResponseEntity.ok(AuthResponse(it)) }
                .switchIfEmpty(Mono.error(PasswordNotMatchException()))

        @PostMapping("/api/v1/regist")
        override fun signin(@RequestBody authRequest: AuthRequest): Mono<ResponseEntity<AuthResponse>> =
            userRepository.save(authRequest.toEntity)
                .map { tokenCreate.createToken(TokenCreateRequest(it.id, it.role)) }
                .map { ResponseEntity.ok(AuthResponse(it)) }
                .switchIfEmpty(Mono.error(PasswordNotMatchException()))

    }

    data class AuthResponse(
        val token: String
    )

    data class AuthRequest(
        val name: String,
        val email: String,
        val password: String
    ) {
        val toEntity
            get() = UserEntity(
                name = name,
                email = email,
                password = password
            )
    }
}
