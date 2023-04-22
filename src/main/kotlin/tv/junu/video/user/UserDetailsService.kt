package tv.junu.video.user

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import tv.junu.video.user.gateway.out.repository.jpa.UserEntity
import tv.junu.video.user.gateway.out.repository.UserRepository

fun interface FindUser {

    fun ByEmail(email: String): Mono<UserEntity>

    @Service
    class UserDetailsService(
        private val userRepository: UserRepository,
    ):FindUser {
        override fun ByEmail(email: String): Mono<UserEntity> = userRepository.findByEmail(email)
    }

}
