package com.afj.solution.buyitapp.service;

import java.util.Base64;
import java.util.UUID;
import javax.servlet.http.Cookie;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

/**
 * @author Tomash Gombosh
 */
@Service
@NoArgsConstructor
@Slf4j
public class AnonymousCookieService {

    public UUID decodeAnonymousCookie(final Cookie cookie) {
        log.info("Decode anonymous cookie {}={}", cookie.getName(), cookie.getValue());
        return UUID.fromString(decode(cookie.getValue()));
    }

    public ResponseCookie generateAnonymousCookie(final UUID temporaryToken) {
        final String domain = System.getenv("APP_DOMAIN");
        log.info("Cookie domain is {}", domain);
        final ResponseCookie anonymousCookie = ResponseCookie
                .from("anonymous", this.encode(temporaryToken.toString()))
                .httpOnly(true)
                .secure(true)
                .path("/")
                .domain(domain)
                .maxAge(1800)
                .build();
        log.info("Generate anonymous cookie {}", anonymousCookie);
        return anonymousCookie;
    }

    private String encode(final String cookie) {
        return Base64.getEncoder().encodeToString(cookie.getBytes());
    }

    private String decode(final String token) {
        final byte[] decodedBytes = Base64.getDecoder().decode(token);
        return new String(decodedBytes);
    }
}
