package tv.junu.video.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class SecurityConfig {
    @Bean
    fun securitygWebFilterChain(
            httpSecurity: ServerHttpSecurity,
    ): SecurityWebFilterChain = httpSecurity
            .formLogin().disable()
            .logout().disable()
            .oauth2ResourceServer { it.jwt() }
            .authorizeExchange()
            .pathMatchers("/api/v1/login", "/api/v1/regist").permitAll()
            .anyExchange().authenticated()
            .and()
            .csrf { it.disable() }
            .build()
            .also { println("securitygWebFilterChain: $it") }
}