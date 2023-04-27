package tv.junu.video.video.gateway.out.http.twitch

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TwitchConfig {

    @Bean
    fun twitchInfo(): TwitchInfo = TwitchInfo()


    data class TwitchInfo(
        val secretToken: String = "jk1yy008rt9jza5xyln8f8r7m5zkqi",
        val clientToken: String = "mx3f2g2kns4gtvvn7udrpz51krpn3x",
    )
}