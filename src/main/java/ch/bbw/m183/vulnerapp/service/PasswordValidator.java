package ch.bbw.m183.vulnerapp.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.regex.Pattern;

@Service
public class PasswordValidator {

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^.{5,}$");

    public void validateForStorage(String password) {
        if (password == null || !PASSWORD_PATTERN.matcher(password).matches()) {
            throw new InvalidPasswordFormatException("Password must be at least 5 characters long.");
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static class InvalidPasswordFormatException extends RuntimeException {
        public InvalidPasswordFormatException(String message) {
            super(message);
        }
    }
}