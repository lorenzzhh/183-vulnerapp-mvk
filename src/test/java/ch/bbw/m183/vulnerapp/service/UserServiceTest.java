package ch.bbw.m183.vulnerapp.service;

import ch.bbw.m183.vulnerapp.datamodel.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	private EntityManager entityManager;

	@Mock
	private Query query;

	private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	private UserService userService;

	@BeforeEach
	void setUp() {
		userService = new UserService(entityManager, passwordEncoder);
	}

	@Test
	void whoamiMatchesBcryptPassword() {
		var user = new UserEntity()
				.setUsername("u")
				.setPassword(passwordEncoder.encode("Strong#Pass1"));
		when(entityManager.createNativeQuery(anyString(), eq(UserEntity.class))).thenReturn(query);
		when(query.setParameter(eq("username"), eq("u"))).thenReturn(query);
		when(query.getSingleResult()).thenReturn(user);

		var result = userService.whoami("u", "Strong#Pass1");

		assertTrue("u".equals(result.getUsername()));
	}

	@Test
	void whoamiFailsForUnknownUser() {
		when(entityManager.createNativeQuery(anyString(), eq(UserEntity.class))).thenReturn(query);
		when(query.setParameter(eq("username"), eq("missing"))).thenReturn(query);
		when(query.getSingleResult()).thenThrow(new NoResultException());

		assertThrows(UserService.InvalidPasswordException.class, () -> userService.whoami("missing", "Strong#Pass1"));
	}
}
