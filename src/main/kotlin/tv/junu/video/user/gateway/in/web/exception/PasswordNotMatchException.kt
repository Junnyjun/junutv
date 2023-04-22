package tv.junu.video.user.gateway.`in`.web.exception

import org.springframework.security.core.AuthenticationException

class PasswordNotMatchException(
    msg: String = "Password not match"
): AuthenticationException(msg)