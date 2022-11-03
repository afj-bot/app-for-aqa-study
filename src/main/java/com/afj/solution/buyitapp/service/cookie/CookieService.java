package com.afj.solution.buyitapp.service.cookie;

import java.util.Base64;
import javax.servlet.http.Cookie;

import org.springframework.http.ResponseCookie;

import static java.util.Objects.requireNonNull;

/**
 * @author Tomash Gombosh
 */
public interface CookieService {

    String decodeCookie(Cookie cookie);

    ResponseCookie generateCookie(String cookieName, String cookieValue);

    default String encode(final String cookie) {
        requireNonNull(cookie, "Cookie value can not be null");

        return Base64.getEncoder().encodeToString(cookie.getBytes());
    }

    default String decode(final String token) {
        final byte[] decodedBytes = Base64.getDecoder().decode(token);
        return new String(decodedBytes);
    }
}
