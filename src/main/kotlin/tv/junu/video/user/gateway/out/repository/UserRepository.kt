package tv.junu.video.user.gateway.out.repository

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import tv.junu.video.user.domain.User
import tv.junu.video.user.exception.UserNotFoundException
import tv.junu.video.user.gateway.out.repository.jpa.UserEntity
import tv.junu.video.user.gateway.out.repository.jpa.UserMongoDBRepository

interface UserRepository {
    fun findByEmail(email: String): Mono<User>
    fun save(userEntity: UserEntity): Mono<User>

    @Component
    @Transactional
    class UserRepositoryImpl(
        private val userMongoDBRepository: UserMongoDBRepository
    ) : UserRepository {
        override fun findByEmail(email: String): Mono<User> =
            userMongoDBRepository.findByEmail(email)
                .map { it.toDomain }
                .switchIfEmpty(Mono.error(UserNotFoundException()))

        override fun save(userEntity: UserEntity): Mono<User> =
            userMongoDBRepository.save(userEntity)
                .map { it.toDomain }
    }
}