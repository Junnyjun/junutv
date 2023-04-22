package tv.junu.video.video.gateway.out.file

import org.junit.jupiter.api.Test
import tv.junu.video.video.gateway.out.MinioConfig

class DeleteVideoTest{
    val gateway  = DeleteVideo.DeleteMinIoGateway(MinioConfig().dev())

    @Test
    fun `delete video`(){
        gateway.delete("test.mp4")
    }
}