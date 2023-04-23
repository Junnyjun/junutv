package tv.junu.video.user

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import reactor.core.publisher.Mono
import tv.junu.video.user.domain.User
import tv.junu.video.user.exception.UserException
import tv.junu.video.user.gateway.out.repository.UserRepository
import tv.junu.video.user.gateway.out.repository.jpa.UserEntity

@ExtendWith(MockitoExtension::class)
class SignUpUserTest {

    @Mock
    private lateinit var userRepository: UserRepository

    @InjectMocks
    private lateinit var signUpUser: SignUpUser.SignUpUserImpl

    @Test
    fun `sign up user`() {
        val user = User(
            email = "mangjoo@gmail.com",
            password = "1234",
            name = "mangjoo",
        )
        `when`(userRepository.save(user))
            .thenReturn(Mono.just(user))

        signUpUser.signUp(user)
            .subscribe {
                assertEquals(it, user.id)
            }
    }

    @Test
    fun `sign up user unique exception test`() {
        val user = User(
            email = "mangjoo@gmail.com",
            password = "1234",
            name = "mangjoo",
        )
        `when`(userRepository.save(user))
            .thenThrow(UserException("User already exists"))

        assertThatThrownBy { signUpUser.signUp(user) }
            .isInstanceOf(UserException::class.java)
            .hasMessage("User already exists")

    }
}