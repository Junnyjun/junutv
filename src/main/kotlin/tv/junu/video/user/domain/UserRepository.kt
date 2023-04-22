package tv.junu.video.user.domain

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import tv.junu.video.user.application.repository.UserEntity
import tv.junu.video.user.application.repository.UserMongoDBRepository
import tv.junu.video.user.exception.UserNotFoundException

interface UserRepository {
    fun findByEmail(email: String): Mono<UserEntity>
    fun save(userEntity: UserEntity): Mono<UserEntity>

    @Component
    @Transactional
    class UserRepositoryImpl(
            private val userMongoDBRepository: UserMongoDBRepository
    ) : UserRepository {
        override fun findByEmail(email: String): Mono<UserEntity> =
                userMongoDBRepository.findByEmail(email) ?: Mono.error(UserNotFoundException())

        override fun save(userEntity: UserEntity): Mono<UserEntity> = userMongoDBRepository.save(userEntity)
    }
}