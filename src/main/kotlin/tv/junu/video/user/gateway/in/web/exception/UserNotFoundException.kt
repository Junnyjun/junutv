package tv.junu.video.user.gateway.`in`.web.exception

class UserNotFoundException(
        msg: String = "User not found"
) : RuntimeException(msg)
