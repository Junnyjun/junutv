package tv.junu.video.user

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import tv.junu.video.user.domain.User
import tv.junu.video.user.gateway.out.repository.UserRepository

fun interface FindUser {

    fun byEmail(email: String): Mono<User>

    @Service
    class UserDetailsService(
        private val userRepository: UserRepository,
    ) : FindUser {
        override fun byEmail(email: String) = userRepository.findByEmail(email)
    }

}
