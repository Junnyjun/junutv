package tv.junu.video.user.gateway.`in`.web

import org.bson.types.ObjectId
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import tv.junu.video.user.SignUpUser
import tv.junu.video.user.domain.User

sealed interface SignUpApi {
    fun signUp(request: SignUpRequest): ResponseEntity<Mono<ObjectId>>

    @RestController
    class SignUpApiImpl(
        private val signUpUser: SignUpUser
    ) : SignUpApi {

        @PostMapping("/api/v1/signup")
        override fun signUp(@RequestBody request: SignUpRequest): ResponseEntity<Mono<ObjectId>> = ResponseEntity.ok(
            takeIf { request.isPasswordConfirm }
                ?.let { signUpUser.signUp(request.toDomain) }
                ?: signUpUser.signUp(request.toDomain)
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

        val toDomain
            get() = User(
                email = email,
                password = password,
                name = name
            )
    }

}