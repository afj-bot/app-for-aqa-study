package com.afj.solution.productApp.security;

import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.HttpServletRequest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.afj.solution.productApp.exception.CustomAuthenticationException;
import com.afj.solution.productApp.model.User;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

/**
 * @author Tomash Gombosh
 */
@Slf4j
@Service
public class JwtTokenProvider {
    private final static String TOKEN = "ASLKJDHALKJSHDLKAJSHDLKAJSHDLKASJHDLKJASHDLKJAHSDLKJHASLDKJh";
    private final String tokenSecret;

    private final ZonedDateTime tokenExpiration;

    public JwtTokenProvider() {
        this.tokenSecret = Base64.getEncoder().encodeToString(TOKEN.getBytes());
        this.tokenExpiration = ZonedDateTime.now().plusSeconds(3600);
    }

    public String createToken(final Map<String, Object> claims) {
        requireNonNull(tokenExpiration, "Initialization of the Token Provider was incorrect use constructor or setter method");
        requireNonNull(tokenSecret, "Initialization of the Token Provider was incorrect use constructor or setter method");

        return this.createToken(claims, tokenExpiration);
    }

    public String createToken(final User user) {
        final Map<String, Object> claims = new ConcurrentHashMap<>();
        claims.put("id", user.getId());
        claims.put("roles", user.getAuthorities());
        return this.createToken(claims);
    }


    public String createToken(final Map<String, Object> claims, final ZonedDateTime expiryDate) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setExpiration(Date.from(expiryDate.toInstant()))
                .signWith(SignatureAlgorithm.HS512, tokenSecret)
                .compact();
    }

    public String getUsernameFromToken(final String token) {
        requireNonNull(tokenSecret, "Initialization of the Token Provider was incorrect use constructor or setter method");

        if (this.validateToken(token)) {
            final Claims claims = Jwts.parser()
                    .setSigningKey(tokenSecret)
                    .parseClaimsJws(token)
                    .getBody();
            return (String) claims.get("username");
        }
        throw new CustomAuthenticationException("Not valid token provided in the request header");
    }

    public String getRoleFromToken(final String token) {
        requireNonNull(tokenSecret, "Initialization of the Token Provider was incorrect use constructor or setter method");

        if (this.validateToken(token)) {
            final Claims claims = Jwts.parser()
                    .setSigningKey(tokenSecret)
                    .parseClaimsJws(token)
                    .getBody();
            return (String) claims.get("role");
        }
        throw new CustomAuthenticationException("Not valid token provided in the request header");
    }

    public UUID getUuidFromToken(final String token) {
        requireNonNull(tokenSecret, "Initialization of the Token Provider was incorrect use constructor or setter method");

        if (this.validateToken(token)) {
            final Claims claims = Jwts.parser()
                    .setSigningKey(tokenSecret)
                    .parseClaimsJws(token)
                    .getBody();
            return UUID.fromString((String) claims.get("id"));
        }
        throw new CustomAuthenticationException("Not valid token provided in the request header");
    }


    public boolean validateToken(final String authToken) {
        requireNonNull(tokenSecret, "Initialization of the Token Provider was incorrect use constructor or setter method");

        try {
            Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token {} {} {}", ex.getMessage(), ex.getClaims().getExpiration(), ex.getClaims().getIssuedAt());
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token {}", ex.getMessage());
        }
        return false;
    }

    public String getJwtFromRequest(final HttpServletRequest request) {
        final String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public SimpleGrantedAuthority transformRole(final String role) {
        return new SimpleGrantedAuthority(String.format("ROLE_%s", role));
    }

    public String getToken() {
        final ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (!isNull(servletRequestAttributes)) {
            final String token = servletRequestAttributes.getRequest().getHeader("Authorization");
            if (!isNull(token) && !"".equals(token) && !" ".equals(token)) {
                return token;
            }
        }
        return "";
    }

    public String getUserUuid() {
        final String getTokenFromRequest = this.getToken();
        if (!isNull(getTokenFromRequest) && !"".equals(getTokenFromRequest) && !" ".equals(getTokenFromRequest)) {
            return this.getUuidFromToken(getTokenFromRequest.substring(7)).toString();

        }
        return "";
    }

}