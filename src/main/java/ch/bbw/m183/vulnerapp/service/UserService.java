package ch.bbw.m183.vulnerapp.service;

import ch.bbw.m183.vulnerapp.datamodel.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import lombok.experimental.StandardException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final EntityManager entityManager;
    private final PasswordEncoder passwordEncoder;

    public UserEntity whoami(String username, String password) {
        // native queries are more performant!!1 :P
		UserEntity user;
		try {
			user = (UserEntity) entityManager
					.createNativeQuery(
							"SELECT * FROM users WHERE username = ?1",
							UserEntity.class)
					.setParameter(1, username)
					.getSingleResult();
		} catch (NoResultException e) {
			throw new InvalidPasswordException("invalid username or password");
		}
		try {
			if (passwordEncoder.matches(password, user.getPassword())) {
				return user;
			}
		} catch (IllegalArgumentException e) {
			log.warn("Stored password format invalid for user {}", user.getUsername());
		}
		throw new InvalidPasswordException("invalid username or password");
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @StandardException
    public static class InvalidPasswordException extends RuntimeException {

    }
}
