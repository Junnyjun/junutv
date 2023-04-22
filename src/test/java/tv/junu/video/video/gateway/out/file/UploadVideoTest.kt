package tv.junu.video.video.gateway.out.file

import org.junit.jupiter.api.Test
import java.nio.file.Path

class UploadVideoTest{
    val gateway = UploadVideo.UploadMinIoGateway(MinioConfig().dev())
    @Test
    fun `upload video`(){
        val upload = gateway.upload(
            UploadVideo.VideoRequest(
                "test.mp4",
                "video/mp4",
                "test",
                Path.of("C:\\workspace\\junutv\\src\\main\\resources\\sample\\sample.mp4").toFile()
            )
        )
        println(upload.etag())
    }
}