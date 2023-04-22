package tv.junu.video.user.gateway.out.jpa

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
interface UserMongoDBRepository: ReactiveMongoRepository<UserEntity, Long> {
    fun findByEmail(email: String): Mono<UserEntity>?
}