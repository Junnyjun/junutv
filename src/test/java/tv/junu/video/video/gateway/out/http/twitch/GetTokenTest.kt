package tv.junu.video.video.gateway.out.http.twitch

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import tv.junu.video.config.WebConfig


class GetTokenTest{
    val getToken = GetToken.GetTokenGateway(
        WebConfig().webClient(),
        TwitchConfig.TwitchInfo()
    )

    @Test
    fun `get token`(){
        val token = getToken.get()
        assertEquals("token", token)
    }
}