package example.service;

import example.model.User;
import example.repository.UserRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

import java.sql.SQLException;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserServiceImpl sut;

  @SneakyThrows
  @Test
  void shouldAddUserWhenUserNotExist() {
    final User user = mock(User.class);
    given(userRepository.isExistsByName(user.getUserName())).willReturn(false);
    sut.addUser(user);
    then(userRepository)
        .should()
        .addUser(user);
  }

  @Test
  void shouldThrowExceptionWhenUserExists() throws SQLException {
    final User user = mock(User.class);
    given(userRepository.isExistsByName(user.getUserName())).willReturn(true);
    final RuntimeException actual = assertThrows(RuntimeException.class, () -> sut.addUser(user));
    assertThat(actual)
        .hasMessage("This login already exists");
  }
}
