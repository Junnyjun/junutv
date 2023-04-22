package tv.junu.video.user.application.repository

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import tv.junu.video.user.application.repository.UserEntity

@Component
interface UserMongoDBRepository: ReactiveMongoRepository<UserEntity, Long> {
    fun findByEmail(email: String): Mono<UserEntity>?
}