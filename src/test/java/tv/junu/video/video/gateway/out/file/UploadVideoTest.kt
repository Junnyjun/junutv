package tv.junu.video.video.gateway.out.file

import org.junit.jupiter.api.Test
import tv.junu.video.video.gateway.out.MinioConfig
import java.nio.file.Path
import java.nio.file.Paths

class UploadVideoTest{
    val gateway = UploadVideo.UploadMinIoGateway(MinioConfig().dev())
    @Test
    fun `upload video`(){
        val get = Paths.get("./")
        val upload = gateway.upload(
            UploadVideo.VideoRequest(
                "test.mp4",
                "video/mp4",
                "test",
                Path.of("sample.mp4").toFile()
            )
        )
        println(upload.etag())
    }
}