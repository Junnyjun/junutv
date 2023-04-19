package tv.junu.video.config

import io.minio.MinioClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
class MinioConfig {

    @Bean
    @Profile("live")
    fun live(): MinioClient = MinioClient.builder()
        .endpoint("http://main.junnyland.com:9000/")
        .credentials("K1VCwb9CEBN3ueQ8", "jAJmrkrL06UfdkStaEC3ZZC3u9tCG2w9")
        .build()

    @Bean
    fun dev(): MinioClient = MinioClient.builder()
        .endpoint("http://main.junnyland.com:9000/")
        .credentials("K1VCwb9CEBN3ueQ8", "jAJmrkrL06UfdkStaEC3ZZC3u9tCG2w9")
        .build()
}