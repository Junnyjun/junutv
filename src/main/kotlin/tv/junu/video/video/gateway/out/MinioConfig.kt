package tv.junu.video.video.gateway.out

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
        .credentials("ng5Wr3xB8xxiZof6", "Kk4gICTFaxj5pifU4uUsA1paHlsPjX1f")
        .build()

    @Bean
    fun dev(): MinioClient = MinioClient.builder()
        .endpoint("http://main.junnyland.com:9000/")
        .credentials("ng5Wr3xB8xxiZof6", "Kk4gICTFaxj5pifU4uUsA1paHlsPjX1f")
        .build()
}