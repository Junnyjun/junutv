package tv.junu.video.video.gateway.out.file

import org.junit.jupiter.api.Test

class DeleteVideoTest{
    val gateway  = DeleteVideo.DeleteMinIoGateway(MinioConfig().dev())

    @Test
    fun `delete video`(){
        gateway.delete("test.mp4")
    }
}