package tv.junu.video.video.gateway.out.file

import io.minio.GetObjectArgs
import io.minio.MinioClient
import org.springframework.stereotype.Component
import java.io.InputStream


interface VideoPart {
    fun get(videoPart: GetVideoRequest): InputStream

    val chunkSize: Long get() = 1024 * 1024

    @Component
    class VideoMinIoGateway(
        private val minioClient: MinioClient,
    ) : VideoPart {

        override fun get(videoPart: GetVideoRequest): InputStream = minioClient.getObject(params(videoPart))

        private fun params(videoPart: GetVideoRequest): GetObjectArgs = GetObjectArgs.builder()
                .bucket("junu-tv")
                .`object`(videoPart.videoName)
                .offset(videoPart.startRange)
                .length(chunkSize.coerceAtMost(videoPart.endRange - videoPart.startRange))
                .build()
    }

    data class GetVideoRequest(
        val videoName: String,
        val startRange: Long,
        val endRange: Long
    )
}