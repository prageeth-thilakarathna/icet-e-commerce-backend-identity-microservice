package edu.icet.demo.security;

import edu.icet.demo.constants.Constants;
import edu.icet.demo.constants.TokenType;
import edu.icet.demo.exception.InternalServerException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final SecretKey key = Jwts.SIG.HS256.key().build();
    private final MessageSource msgSrc;

    public String generateToken(UserDetails userDetails, TokenType tokenType) {
        Map<String, Object> content = new HashMap<>();
        Calendar calendar = Calendar.getInstance();

        JwtBuilder jwtBuilder = Jwts.builder()
                .header()
                .add("name", "Cogli")
                .and()
                .subject(userDetails.getUsername())
                .claims(content)
                .issuedAt(new Date(System.currentTimeMillis()));

        if (tokenType == TokenType.BEARER) {
            calendar.add(Calendar.DATE, 1);
            Date expDate = calendar.getTime();
            jwtBuilder.expiration(expDate);
        }

        if (tokenType == TokenType.RESET_PASSWORD) {
            calendar.add(Calendar.HOUR, 1);
            Date expDate = calendar.getTime();
            jwtBuilder.expiration(expDate);
        }

        if (tokenType == TokenType.VERIFICATION) {
            calendar.add(Calendar.MINUTE, 10);
            Date expDate = calendar.getTime();
            jwtBuilder.expiration(expDate);
        }

        return jwtBuilder.signWith(key).compact();
    }

    public Date getExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getTokenBody(token);
        return claimsResolver.apply(claims);
    }

    private Claims getTokenBody(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

        } catch (JwtException e) {
            throw new InternalServerException(msgSrc.getMessage(Constants.INTERNAL_SERVER_ERROR,
                    new Object[]{e.getMessage()}, Locale.ENGLISH));
        }
    }
}
