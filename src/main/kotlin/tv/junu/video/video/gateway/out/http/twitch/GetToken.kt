package tv.junu.video.video.gateway.out.http.twitch

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import tv.junu.video.video.gateway.out.http.twitch.TwitchConfig.TwitchInfo

interface GetToken {
    fun get(): String

    @Component
    class GetTokenGateway(
        private val webClient: WebClient,
        private val twitchInfo: TwitchInfo
    ) : GetToken {
        private val tokenUrl = "/token?client_id=${twitchInfo.clientToken}&client_secret=${twitchInfo.secretToken}&grant_type=client_credentials"
        override fun get() = webClient
            .post()
            .uri(tokenUrl)
            .retrieve()
            .bodyToMono(TokenResponse::class.java)
            .block()
            ?.accessToken
            ?: throw RuntimeException("Token is null")

    }

    data class TokenResponse(
        @JsonProperty("access_token") val accessToken: String = "",
        @JsonProperty("expires_in") val expiresIn: Long = 0,
        @JsonProperty("token_type") val tokenType: String = ""
    )
}