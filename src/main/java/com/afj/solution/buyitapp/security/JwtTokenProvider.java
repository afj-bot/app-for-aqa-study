package com.afj.solution.buyitapp.security;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.List;
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

import com.afj.solution.buyitapp.exception.CustomAuthenticationException;
import com.afj.solution.buyitapp.model.User;

import static com.afj.solution.buyitapp.constans.Time.DEFAULT_TOKEN_EXPIRED_TIME;
import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

/**
 * @author Tomash Gombosh
 */
@Slf4j
@Service
public class JwtTokenProvider {
    private static final String TOKEN = "ASLKJDHALKJSHDLKAJSHDLKAJSHDLKASJHDLKJASHDLKJAHSDLKJHASLDKJh";
    private final String tokenSecret;

    private final long tokenExpiration;

    public JwtTokenProvider() {
        this.tokenSecret = Base64.getEncoder().encodeToString(TOKEN.getBytes());
        this.tokenExpiration = DEFAULT_TOKEN_EXPIRED_TIME.getSeconds();
    }

    public String createToken(final Map<String, Object> claims) {
        requireNonNull(tokenSecret, "Initialization of the Token Provider was incorrect use constructor or setter method");

        return this.createToken(claims, tokenExpiration);
    }

    public String createToken(final User user) {
        final Map<String, Object> claims = new ConcurrentHashMap<>();
        claims.put("id", user.getId());
        claims.put("roles", user.getAuthorities());
        claims.put("username", user.getUsername());
        return this.createToken(claims);
    }


    public String createToken(final Map<String, Object> claims, final long expirySeconds) {
        final ZonedDateTime now = ZonedDateTime.now().withZoneSameInstant(ZoneId.of(ZoneOffset.UTC.getId()));
        final ZonedDateTime expiryDate = now.plusSeconds(expirySeconds);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now.toInstant()))
                .setExpiration(Date.from(expiryDate.toInstant()))
                .signWith(SignatureAlgorithm.HS512, tokenSecret)
                .compact();
    }

    public String getUsernameFromToken(final String token) {
        requireNonNull(tokenSecret, "Initialization of the Token Provider was incorrect use constructor or setter method");

        if (!token.isEmpty() && this.validateToken(token)) {
            final Claims claims = Jwts.parser()
                    .setSigningKey(tokenSecret)
                    .parseClaimsJws(token)
                    .getBody();
            return (String) claims.get("username");
        }
        throw new CustomAuthenticationException("error.token.invalid");
    }

    public List<Map<String, String>> getRoleFromToken(final String token) {
        requireNonNull(tokenSecret, "Initialization of the Token Provider was incorrect use constructor or setter method");

        if (!token.isEmpty() && this.validateToken(token)) {
            final Claims claims = Jwts.parser()
                    .setSigningKey(tokenSecret)
                    .parseClaimsJws(token)
                    .getBody();
            return (List<Map<String, String>>) claims.get("roles");
        }
        throw new CustomAuthenticationException("error.token.invalid");
    }

    public UUID getUuidFromToken(final String token) {
        requireNonNull(tokenSecret, "Initialization of the Token Provider was incorrect use constructor or setter method");

        if (!token.isEmpty() && this.validateToken(token)) {
            final Claims claims = Jwts.parser()
                    .setSigningKey(tokenSecret)
                    .parseClaimsJws(token)
                    .getBody();
            return UUID.fromString((String) claims.get("id"));
        }
        throw new CustomAuthenticationException("error.token.invalid");
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
        return "Bearer ";
    }

    public String getUserUuid() {
        final String getTokenFromRequest = this.getToken();
        if (!isNull(getTokenFromRequest) && !"".equals(getTokenFromRequest) && !" ".equals(getTokenFromRequest)) {
            return this.getUuidFromToken(getTokenFromRequest.substring(7)).toString();

        }
        return "";
    }

}
