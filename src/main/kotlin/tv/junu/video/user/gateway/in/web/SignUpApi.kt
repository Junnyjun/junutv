package tv.junu.video.user.gateway.`in`.web

import org.bson.types.ObjectId
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import tv.junu.video.user.SignUpUser
import tv.junu.video.user.domain.User
import tv.junu.video.user.exception.PasswordNotMatchException

sealed interface SignUpApi {
    fun signUp(request: SignUpRequest): ResponseEntity<Mono<ObjectId>>

    @RestController
    class SignUpApiImpl(
        private val signUpUser: SignUpUser,
        private val passwordEncoder: PasswordEncoder
    ) : SignUpApi {

        @PostMapping("/api/v1/signup")
        override fun signUp(@RequestBody request: SignUpRequest): ResponseEntity<Mono<ObjectId>> = ResponseEntity.ok(
            takeIf { request.isPasswordConfirm }
                ?.let { signUpUser.signUp(request.toDomain(passwordEncoder)) }
                ?: throw PasswordNotMatchException("Password and PasswordConfirm are not matched")
        )

    }


    data class SignUpRequest(
        val email: String,
        val password: String,
        val passwordConfirm: String,
        val name: String
    ) {
        val isPasswordConfirm
            get() = password == passwordConfirm

        fun toDomain(passwordEncoder: PasswordEncoder) = User(
                email = email,
                password = passwordEncoder.encode(password),
                name = name
            )
    }

}