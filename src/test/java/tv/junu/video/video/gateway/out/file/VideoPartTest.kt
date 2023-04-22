package tv.junu.video.video.gateway.out.file

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import tv.junu.video.config.MinioConfig
import tv.junu.video.video.gateway.out.file.VideoPart.GetVideoRequest
import java.io.File
import java.io.FileOutputStream
import java.util.*

class VideoPartTest{
    val videoPart = VideoPart.VideoMinIoGateway(MinioConfig().dev())
    val filePath= "./sample-${UUID.randomUUID()}.mp4"
    @Test
    fun `get video part`(){
        val videoPart = videoPart.get(
            GetVideoRequest(
                "test.mp4",
                0,
                3114374
            )
        )
        val file = File(filePath)
        val fileOutputStream = FileOutputStream(file)
        videoPart.copyTo(fileOutputStream)
    }

    @AfterEach
    fun `delete file`(){
        File(filePath).delete()
    }
}