package tv.junu.video.user

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import tv.junu.video.user.gateway.out.repository.jpa.UserEntity
import tv.junu.video.user.gateway.out.repository.UserRepository

@Service
class UserDetailsService(
    private val userRepository: UserRepository,
) {
    fun loadUserByUsername(email: String): Mono<UserEntity> =
            userRepository.findByEmail(email)
}
