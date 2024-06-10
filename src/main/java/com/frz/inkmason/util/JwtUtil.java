package com.frz.inkmason.util;
import com.frz.inkmason.enums.StatusCode;
import com.frz.inkmason.model.person.User;
import com.frz.inkmason.repository.UserRepository;
import com.frz.inkmason.response.BodyResponse;
import com.frz.inkmason.response.LocalResponse;
import com.frz.inkmason.response.Response;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;
import java.util.function.Function;

@RequiredArgsConstructor
@Service
public class JwtUtil {
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.expirationTime}")
    private int TOKEN_TIME;

    private UserRepository userRepository;

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isValid(String token, User user){
        String username = extractUsername(token);
        return (username.equals(user.getUsername()) && isTokenExpired(token));
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token){
        return extractClaim(token,Claims::getExpiration);
    }
    public <T> T extractClaim(String token, Function<Claims, T> resolver){
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String generateToken(User user){

        return Jwts
                .builder()
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + TOKEN_TIME))
                .signWith(getSignInKey())
                .compact();

    }

    private SecretKey getSignInKey(){
        byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public BodyResponse<User> getUserFromToken(String token){
        if(token == null || !token.startsWith("Bearer")){
            return new BodyResponse<>(StatusCode.unauthorized.getCode(),  "No Authorization Token",null);
        }

        String username = extractUsername(token.substring(7));

        User user = userRepository.findUserByEmail(username).orElse(null);
        if(user == null){
            return new BodyResponse<>(StatusCode.badRequest.getCode(), "User not Found",null);
        }

        return new BodyResponse<>(StatusCode.successful.getCode(), "ok"
        ,user);
    }

}
