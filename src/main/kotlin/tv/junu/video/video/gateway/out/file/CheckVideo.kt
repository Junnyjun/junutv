package tv.junu.video.video.gateway.out.file

import io.minio.MinioClient
import io.minio.StatObjectArgs
import io.minio.StatObjectResponse

interface CheckVideo {
    fun check(videoName: String): VideoInfo

    class CheckMinIoGateway(
        private val minioClient: MinioClient,
    ) : CheckVideo {
        override fun check(videoName: String) = minioClient.statObject(params(videoName))
            ?.let { VideoInfo.from(it) }
            ?: throw RuntimeException("video not found")

        private fun params(videoName: String): StatObjectArgs = StatObjectArgs.builder()
                .bucket("junu-tv")
                .`object`(videoName)
                .build()
    }

    data class VideoInfo(
        val name: String,
        val contentType: String,
        val tag: String,
        val length: Long,
    ) {
        companion object {
            fun from(response: StatObjectResponse) = VideoInfo(
                name = response.`object`(),
                contentType = response.contentType(),
                tag = response.etag(),
                length = response.size(),
            )
        }
    }
}