package tv.junu.video.config

import com.mongodb.reactivestreams.client.MongoClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory
import org.springframework.data.mongodb.ReactiveMongoTransactionManager
import org.springframework.data.mongodb.config.EnableMongoAuditing
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories


@Configuration
@EnableMongoRepositories(basePackages = ["tv.junu.video"])
@EnableMongoAuditing
class MongodbConfig {

    @Bean
    fun transactionManager(dbFactory: ReactiveMongoDatabaseFactory): ReactiveMongoTransactionManager =
            ReactiveMongoTransactionManager(dbFactory)


    @Bean
    fun reactiveMongoTemplate(mongoClient: MongoClient): ReactiveMongoTemplate =
            ReactiveMongoTemplate(SimpleReactiveMongoDatabaseFactory(mongoClient, "junutv"))
//        val factory: MongoDatabaseFactory = SimpleMongoClientDatabaseFactory(mongoClient, "junutv")
//        return MongoTemplate(factory)

}