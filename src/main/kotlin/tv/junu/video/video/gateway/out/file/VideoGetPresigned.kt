package tv.junu.video.video.gateway.out.file

import io.minio.GetPresignedObjectUrlArgs
import io.minio.MinioClient
import io.minio.http.Method
import org.springframework.stereotype.Component

interface VideoGetPresigned {
    fun get(videoPart: GetPresignedVideoRequest): String

    val chunkSize: Long get() = 1024 * 1024

    @Component
    class PresignedVideoMinIoGateway(
        private val minioClient: MinioClient,
    ) : VideoGetPresigned {

        override fun get(videoPart: GetPresignedVideoRequest): String = minioClient.getPresignedObjectUrl(params(videoPart))

        private fun params(videoPart: GetPresignedVideoRequest): GetPresignedObjectUrlArgs = GetPresignedObjectUrlArgs.builder()
            .bucket("junu-tv")
            .`object`(videoPart.videoName)
            .expiry(500000)
            .method(Method.GET)
            .extraHeaders(mapOf("Range" to "bytes=${videoPart.startRange}-${videoPart.endRange}"))
            .extraQueryParams(mapOf("response-content-type" to "video/mp4"))
            .build()
    }

    data class GetPresignedVideoRequest(
        val videoName: String,
        val startRange: Long,
        val endRange: Long
    )
}