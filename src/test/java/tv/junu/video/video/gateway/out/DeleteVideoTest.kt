package tv.junu.video.video.gateway.out

import org.junit.jupiter.api.Test
import tv.junu.video.config.MinioConfig
import tv.junu.video.video.gateway.out.file.DeleteVideo

class DeleteVideoTest{
    val gateway  = DeleteVideo.DeleteMinIoGateway(MinioConfig().dev())

    @Test
    fun `delete video`(){
        gateway.delete("test.mp4")
    }
}