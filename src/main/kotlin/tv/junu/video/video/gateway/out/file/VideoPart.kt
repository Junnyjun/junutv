package tv.junu.video.video.gateway.out.file

import io.minio.MinioClient
import org.springframework.stereotype.Component


interface VideoPart {
    fun get(videoPart: GetVideoRequest): ByteArray

    private val chunkSize: Long get() = 1024 * 1024

    @Component
    class VideoMinIoGateway(
        private val minioClient: MinioClient,
    ): VideoPart {

        override fun get(videoPart: GetVideoRequest): ByteArray {
            TODO("Not yet implemented")
        }

    }

    data class GetVideoRequest(
        val videoName: String,
        val startRange:Long,
    )
}