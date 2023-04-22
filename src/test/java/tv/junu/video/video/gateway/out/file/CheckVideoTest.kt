package tv.junu.video.video.gateway.out.file

import org.junit.jupiter.api.Test

class CheckVideoTest{
    val gateway = CheckVideo.CheckMinIoGateway(MinioConfig().dev())

    @Test
    fun `check video`(){
        val check = gateway.check("test.mp4")
        println(check)
    }
}