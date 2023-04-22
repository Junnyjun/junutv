package tv.junu.video.user.exception

class UserNotFoundException(
        msg: String = "User not found"
) : RuntimeException(msg)
