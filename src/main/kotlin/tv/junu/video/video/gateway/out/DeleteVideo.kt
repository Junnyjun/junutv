package tv.junu.video.video.gateway.out

import io.minio.MinioClient
import io.minio.RemoveObjectArgs
import org.springframework.stereotype.Component

interface DeleteVideo {
    fun delete(videoName: String)

    @Component
    class DeleteMinIoGateway(
        private val minioClient: MinioClient,
    ) : DeleteVideo {
        override fun delete(videoName: String) = minioClient.removeObject(
            RemoveObjectArgs.builder()
                .bucket("junu-tv")
                .`object`(videoName)
                .build()
        )
    }

}