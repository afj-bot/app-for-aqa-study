package com.afj.solution.buyitapp.integration;

import java.util.Set;
import java.util.UUID;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ActiveProfiles;

import com.afj.solution.buyitapp.model.User;
import com.afj.solution.buyitapp.service.user.UserService;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
@ActiveProfiles("local")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseTest implements WithAssertions {
    @Autowired
    private UserService userService;

    @LocalServerPort
    private int port;

    protected TestRestTemplate restTemplate = new TestRestTemplate();

    @BeforeAll
    void userAuthoritySetup() {
        final User user = userService.findById(UUID.fromString("3b0a4223-35e6-47b1-9ac3-f95911979573"));
        user.setAuthorities(Set.of(new SimpleGrantedAuthority("ROLE_ADMIN"),
                new SimpleGrantedAuthority("ROLE_USER")));
        userService.updateUser(user.getId(), user);
    }

    protected String createUrlWithPort(final String uri) {
        return "http://localhost:" + port + uri;
    }
}
