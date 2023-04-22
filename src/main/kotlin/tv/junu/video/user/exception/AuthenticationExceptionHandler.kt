package tv.junu.video.user.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import reactor.core.publisher.Mono

@RestControllerAdvice
class AuthenticationExceptionHandler {

    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFoundException(): Mono<ResponseEntity<String>> =
            Mono.just(ResponseEntity.badRequest().body("Can't login user"))
}