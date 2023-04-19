package tv.junu.video.video.gateway.out

import io.minio.MinioClient
import io.minio.ObjectWriteResponse
import io.minio.PutObjectArgs
import org.springframework.stereotype.Component
import java.io.File

interface UploadVideo {
    fun upload(video: VideoRequest): ObjectWriteResponse

    @Component
    class UploadMinIoGateway(
        private val minioClient: MinioClient,
    ) : UploadVideo {
        override fun upload(video: VideoRequest) = minioClient.putObject(
            PutObjectArgs.builder()
                .bucket("junu-tv")
                .`object`(video.name)
                .contentType("video/mp4")
                .tags(mapOf("category" to video.tag))
                .stream(video.file.inputStream(), video.file.length(), -1)
                .build()
        )
    }

    data class VideoRequest(
        val name: String,
        val contentType: String,
        val tag: String,
        val file: File,
    )
}