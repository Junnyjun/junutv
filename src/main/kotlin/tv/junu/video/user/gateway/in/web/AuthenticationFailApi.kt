package tv.junu.video.user.gateway.`in`.web

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import reactor.core.publisher.Mono
import tv.junu.video.user.exception.PasswordNotMatchException
import tv.junu.video.user.exception.UserException
import tv.junu.video.user.exception.UserNotFoundException

@RestControllerAdvice
class AuthenticationFailApi {

    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFoundException(): Mono<ResponseEntity<String>> =
            Mono.just(ResponseEntity.badRequest().body("Can't login user"))

    @ExceptionHandler(PasswordNotMatchException::class)
    fun handlePasswordNotMatchException(): Mono<ResponseEntity<String>> =
            Mono.just(ResponseEntity.badRequest().body("Password not match"))

    @ExceptionHandler(UserException::class)
    fun handleUserException(exception: UserException): Mono<ResponseEntity<String>> =
            Mono.just(ResponseEntity.badRequest().body(exception.message))
}