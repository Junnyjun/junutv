package tv.junu.video.user.gateway.out.repository

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import tv.junu.video.user.gateway.out.repository.jpa.UserEntity
import tv.junu.video.user.gateway.out.repository.jpa.UserMongoDBRepository
import tv.junu.video.user.gateway.`in`.web.exception.UserNotFoundException

interface UserRepository {
    fun findByEmail(email: String): Mono<UserEntity>
    fun save(userEntity: UserEntity): Mono<UserEntity>

    @Component
    @Transactional
    class UserRepositoryImpl(
            private val userMongoDBRepository: UserMongoDBRepository
    ) : UserRepository {
        override fun findByEmail(email: String): Mono<UserEntity> =
                userMongoDBRepository.findByEmail(email)
                    .map { it.toDomain }
                    .switchIfEmpty(Mono.error(UserNotFoundException()))

        override fun save(userEntity: UserEntity): Mono<UserEntity> = userMongoDBRepository.save(userEntity)
    }
}