package tv.junu.video.video.gateway.out

import io.minio.MinioClient
import org.springframework.stereotype.Component

interface VideoPart {
    fun get(videoPart: GetVideoRequest): ByteArray

    @Component
    class VideoMinIoGateway(
        private val minioClient: MinioClient,
    ):VideoPart{
        override fun get(videoPart: GetVideoRequest): ByteArray {
            TODO("Not yet implemented")
        }

    }

    data class GetVideoRequest(
        val videoName: String,
        val startRange:Long,
    )
}