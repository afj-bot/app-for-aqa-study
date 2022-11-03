package com.afj.solution.buyitapp.service.cookie;

import javax.servlet.http.Cookie;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

/**
 * @author Tomash Gombosh
 */
@Service
@NoArgsConstructor
@Slf4j
public class AnonymousCookieService implements CookieService {

    @Value("${app.domain.url}")
    private String appDomain;

    @Override
    public String decodeCookie(final Cookie cookie) {
        log.info("Decode anonymous cookie {}={}", cookie.getName(), cookie.getValue());
        return decode(cookie.getValue());
    }

    @Override
    public ResponseCookie generateCookie(final String cookieName, final String cookieValue) {
        final String domain = appDomain;
        log.info("Cookie domain is {}", domain);
        final ResponseCookie anonymousCookie = ResponseCookie
                .from(cookieName, this.encode(cookieValue))
                .httpOnly(true)
                .secure(true)
                .path("/")
                .domain(domain)
                .maxAge(1800)
                .build();
        log.info("Generate anonymous cookie {}", anonymousCookie);
        return anonymousCookie;
    }
}
