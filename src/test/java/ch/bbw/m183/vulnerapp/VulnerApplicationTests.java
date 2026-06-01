package ch.bbw.m183.vulnerapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class VulnerApplicationTests {

	@Autowired
	WebTestClient webTestClient;

	@Test
	void landingPage() {
		webTestClient.get().uri("/").exchange().expectStatus().isOk();
	}

	@Test
	void adminEndpointsRequireAdminRole() {
		webTestClient.get().uri("/api/admin123/users")
				.header(HttpHeaders.AUTHORIZATION, basicAuth("fuu", "bar123"))
				.exchange()
				.expectStatus().isForbidden();

		webTestClient.get().uri("/api/admin123/users")
				.header(HttpHeaders.AUTHORIZATION, basicAuth("admin", "super5ecret"))
				.exchange()
				.expectStatus().isOk();
	}

	@Test
	void userEndpointsAllowUserRole() {
		webTestClient.get().uri("/api/user/whoami")
				.header(HttpHeaders.AUTHORIZATION, basicAuth("fuu", "bar123"))
				.exchange()
				.expectStatus().isOk();
	}

	private static String basicAuth(String username, String password) {
		var credentials = username + ":" + password;
		return "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
	}
}
