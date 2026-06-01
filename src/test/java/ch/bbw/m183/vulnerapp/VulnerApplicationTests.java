package ch.bbw.m183.vulnerapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class VulnerApplicationTests {

	@Autowired
	WebTestClient webTestClient;

	private static final String USERNAME_USER = "fuu";
	private static final String PASSWORD_USER = "bar123";

	private static final String USERNAME_ADMIN = "admin";
	private static final String PASSWORD_ADMIN = "super5ecret";

	// ----------------------------------------------------
	// Öffentliche Endpunkte
	// ----------------------------------------------------

	@Test
	void landingPage_anonymous_shouldReturn200() {
		webTestClient.get()
				.uri("/")
				.exchange()
				.expectStatus()
				.isOk();
	}

	@Test
	void blogGet_anonymous_shouldReturn200() {
		webTestClient.get()
				.uri("/api/blog")
				.exchange()
				.expectStatus()
				.isOk();
	}

	@Test
	void actuatorHealth_anonymous_shouldReturn200() {
		webTestClient.get()
				.uri("/actuator/health")
				.exchange()
				.expectStatus()
				.isOk();
	}

	// ----------------------------------------------------
	// /api/user/**
	// ----------------------------------------------------

	@Test
	void whoAmI_anonymous_shouldReturn401() {
		webTestClient.get()
				.uri("/api/user/whoami")
				.exchange()
				.expectStatus()
				.isUnauthorized();
	}

	@Test
	void whoAmI_user_shouldReturn200() {
		webTestClient.get()
				.uri("/api/user/whoami")
				.headers(headers ->
						headers.setBasicAuth(USERNAME_USER, PASSWORD_USER))
				.exchange()
				.expectStatus()
				.isOk();
	}

	@Test
	void whoAmI_admin_shouldReturn200() {
		webTestClient.get()
				.uri("/api/user/whoami")
				.headers(headers ->
						headers.setBasicAuth(USERNAME_ADMIN, PASSWORD_ADMIN))
				.exchange()
				.expectStatus()
				.isOk();
	}

	// ----------------------------------------------------
	// /api/admin123/**
	// ----------------------------------------------------

	@Test
	void admin_anonymous_shouldReturn401() {
		webTestClient.get()
				.uri("/api/admin123/test")
				.exchange()
				.expectStatus()
				.isUnauthorized();
	}

	@Test
	void admin_user_shouldReturn403() {
		webTestClient.get()
				.uri("/api/admin123/test")
				.headers(headers ->
						headers.setBasicAuth(USERNAME_USER, PASSWORD_USER))
				.exchange()
				.expectStatus()
				.isForbidden();
	}

	@Test
	void admin_admin_shouldReturn200() {
		webTestClient.get()
				.uri("/api/admin123/users")
				.headers(headers ->
						headers.setBasicAuth(USERNAME_ADMIN, PASSWORD_ADMIN))
				.exchange()
				.expectStatus()
				.isOk();
	}

	// ----------------------------------------------------
	// POST /api/blog
	// ----------------------------------------------------

	@Test
	void postBlog_anonymous_shouldReturn401() {
		webTestClient.post()
				.uri("/api/blog")
				.exchange()
				.expectStatus()
				.isForbidden();
	}

	@Test
	void postBlog_user_withoutCsrf_shouldReturn403() {
		webTestClient.post()
				.uri("/api/blog")
				.headers(headers ->
						headers.setBasicAuth(USERNAME_USER, PASSWORD_USER))
				.exchange()
				.expectStatus()
				.isForbidden();
	}

	@Test
	void postBlog_admin_withoutCsrf_shouldReturn403() {
		webTestClient.post()
				.uri("/api/blog")
				.headers(headers ->
						headers.setBasicAuth(USERNAME_ADMIN, PASSWORD_ADMIN))
				.exchange()
				.expectStatus()
				.isForbidden();
	}
}