package com.afj.solution.buyitapp.unit.service.user;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;

import com.afj.solution.buyitapp.service.cookie.AnonymousCookieService;
import com.afj.solution.buyitapp.service.user.UserAuthService;
import com.afj.solution.buyitapp.unit.BaseTest;

/**
 * @author Tomash Gombosh
 */
@DisplayName("User auth service tests")
class UserAuthServiceTest extends BaseTest {

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private AnonymousCookieService anonymousCookieService;

    @Value("${token.secret}")
    private String tokenSecret;

    @Value("${token.expiration.anonymous}")
    private long anonymousTokenExpiration;

    @Test
    @DisplayName("Login Anonymous")
    void loginAnonymous() {
        final ResponseCookie cookie = anonymousCookieService.generateCookie("anonymous", userId.toString());
        final String token = userAuthService.loginAnonymous(cookie.getValue(), userId);
        final Claims claims = Jwts.parser()
                .setSigningKey(tokenSecret.getBytes())
                .parseClaimsJws(token)
                .getBody();
        assertThat(ZonedDateTime.ofInstant(claims.getExpiration().toInstant(),
                ZoneId.systemDefault()))
                .isBeforeOrEqualTo(ZonedDateTime.now().plusHours(anonymousTokenExpiration));
    }
}
