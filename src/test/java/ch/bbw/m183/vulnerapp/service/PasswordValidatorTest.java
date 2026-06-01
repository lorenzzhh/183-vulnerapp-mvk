package ch.bbw.m183.vulnerapp.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PasswordValidatorTest {

	private final PasswordValidator passwordValidator = new PasswordValidator();

	@Test
	void acceptsStrongPassword() {
		assertDoesNotThrow(() -> passwordValidator.validateForStorage("Strong#Pass1"));
	}

	@Test
	void rejectsWeakPassword() {
		assertThrows(PasswordValidator.InvalidPasswordFormatException.class,
				() -> passwordValidator.validateForStorage("weak"));
	}
}
