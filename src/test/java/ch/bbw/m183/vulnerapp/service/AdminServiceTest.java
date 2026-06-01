package ch.bbw.m183.vulnerapp.service;

import ch.bbw.m183.vulnerapp.datamodel.UserEntity;
import ch.bbw.m183.vulnerapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

	@Mock
	private UserRepository userRepository;

	private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	private final PasswordValidator passwordValidator = new PasswordValidator();

	private AdminService adminService;

	@BeforeEach
	void setUp() {
		adminService = new AdminService(userRepository, passwordEncoder, passwordValidator);
	}

	@Test
	void createUserStoresHashedPassword() {
		var user = new UserEntity().setUsername("u").setFullname("User").setPassword("Strong#Pass1");
		when(userRepository.save(any(UserEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

		adminService.createUser(user);

		var captor = ArgumentCaptor.forClass(UserEntity.class);
		verify(userRepository).save(captor.capture());
		var savedUser = captor.getValue();
		assertNotEquals("Strong#Pass1", savedUser.getPassword());
	}

	@Test
	void createUserRejectsWeakPassword() {
		var user = new UserEntity().setUsername("u").setFullname("User").setPassword("weak");

		assertThrows(PasswordValidator.InvalidPasswordFormatException.class,
				() -> adminService.createUser(user));
	}
}
