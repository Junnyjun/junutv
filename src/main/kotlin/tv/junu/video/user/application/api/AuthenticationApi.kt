package tv.junu.video.user.application.api

import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import tv.junu.video.user.domain.jwt.JwtCreate
import tv.junu.video.user.application.repository.Role.*
import tv.junu.video.user.application.repository.UserEntity
import tv.junu.video.user.domain.UserRepository
import tv.junu.video.user.application.service.UserDetailsService
import tv.junu.video.user.exception.PasswordNotMatchException

@RestController
class AuthenticationApi(
        private val jwtCreate: JwtCreate,
        private val passwordEncoder: PasswordEncoder,
        private val userDetailsService: UserDetailsService,
        private val userRepository: UserRepository
) {
    @PostMapping("/api/v1/login")
    fun customLogin(@RequestBody authRequest: AuthRequest): Mono<ResponseEntity<AuthResponse>> = userDetailsService.loadUserByUsername(authRequest.email)
            .filter { passwordEncoder.matches(authRequest.password, it.password) }
            .map { ResponseEntity.ok(AuthResponse(jwtCreate.createToken(it.id, it.role))) }
            .switchIfEmpty(Mono.error(PasswordNotMatchException()))

    @PostMapping("/api/v1/signup")
    fun customSignup(@RequestBody authRequest: AuthRequest): Mono<ResponseEntity<AuthResponse>> = userDetailsService.loadUserByUsername(authRequest.email)
            .filter { passwordEncoder.matches(authRequest.password, it.password) }
            .map { ResponseEntity.ok(AuthResponse(jwtCreate.createToken(it.id, it.role))) }

    @PostMapping("/api/v1/regist")
    fun customRegist(@RequestBody authRequest: AuthRequest): Mono<ResponseEntity<AuthResponse>> = userRepository.save(
            UserEntity(
            email = authRequest.email,
            password = passwordEncoder.encode(authRequest.password),
    ))
            .map { ResponseEntity.ok(AuthResponse(jwtCreate.createToken(it.id, it.role))) }

}

data class AuthResponse(
        val token: String
)

data class AuthRequest(
        val email: String,
        val password: String
)