package tv.junu.video.user.application.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import tv.junu.video.user.application.repository.UserEntity
import tv.junu.video.user.domain.UserRepository

@Service
class UserDetailsService(
        private val userRepository: UserRepository,
) {
    fun loadUserByUsername(email: String): Mono<UserEntity> =
            userRepository.findByEmail(email)
}
