package tv.junu.video.user

import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import tv.junu.video.user.domain.User
import tv.junu.video.user.exception.UserException
import tv.junu.video.user.gateway.out.repository.UserRepository
import tv.junu.video.user.gateway.out.repository.jpa.UserEntity

interface SignUpUser {

    fun signUp(user: User): Mono<ObjectId>

    @Service
    class SignUpUserImpl(
        private val userRepository: UserRepository,
    ) : SignUpUser {
        override fun signUp(user: User): Mono<ObjectId> =
            userRepository.save(user)
                .map { it.id }
                .switchIfEmpty(Mono.error(UserException("User already exists")))
    }
}