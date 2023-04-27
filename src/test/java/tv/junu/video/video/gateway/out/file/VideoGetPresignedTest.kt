package tv.junu.video.video.gateway.out.file

import org.junit.jupiter.api.Test
import tv.junu.video.video.gateway.out.MinioConfig
import tv.junu.video.video.gateway.out.file.VideoGetPresigned.GetPresignedVideoRequest

class VideoGetPresignedTest{
    val videoPart = VideoGetPresigned.PresignedVideoMinIoGateway(MinioConfig().dev())
    @Test
    fun `get video part`(){
        val videoPart = videoPart.get(GetPresignedVideoRequest(
                "test.mp4",
                0,
                1000000
            )
        )
        println(videoPart)
    }
}