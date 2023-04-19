package tv.junu.video.video.gateway.out

import io.minio.MinioClient
import io.minio.StatObjectArgs
import io.minio.StatObjectResponse

interface CheckVideo {
    fun check(videoName: String): StatObjectResponse

    class CheckMinIoGateway(
        private val minioClient: MinioClient,
    ) : CheckVideo {
        override fun check(videoName: String) = minioClient.statObject(
            StatObjectArgs.builder()
                .bucket("junu-tv")
                .`object`(videoName)
                .build()
        )
    }
}