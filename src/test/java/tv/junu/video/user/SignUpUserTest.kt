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
        val userEntity = UserEntity(
            email = "mangjoo@gmail.com",
            password = "1234",
            name = "mangjoo",
        )
        `when`(userRepository.save(userEntity))
            .thenReturn(Mono.just(userEntity.toDomain))

        signUpUser.signUp(userEntity)
            .subscribe {
                assertEquals(it, userEntity.toDomain.id)
            }
    }

    @Test
    fun `sign up user unique exception test`() {
        val userEntity = UserEntity(
            email = "mangjoo@gmail.com",
            password = "1234",
            name = "mangjoo",
        )
        `when`(userRepository.save(userEntity))
            .thenThrow(UserException("User already exists"))

        assertThatThrownBy { signUpUser.signUp(userEntity) }
            .isInstanceOf(UserException::class.java)
            .hasMessage("User already exists")

    }
}