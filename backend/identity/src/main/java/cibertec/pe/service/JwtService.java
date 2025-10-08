package cibertec.pe.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cibertec.pe.model.UsuarioCredential;
import cibertec.pe.repository.IUsuarioCredential;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;

@Component
public class JwtService {

	@Autowired
	private IUsuarioCredential repository;
	
	
    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    public void validateToken(final String token) {
    	Jwts
        .parser()
        .verifyWith(getSignKey())
        .build()
        .parseSignedClaims(token);
    }

    public String generateToken(String username) {
        UsuarioCredential user = repository.findByEmail(username)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Map<String, Object> claims = new HashMap<>();
        claims.put("rol", user.getRol().name());

        return generateTokenWithClaims(claims, username);
    }

    private String createToken(Map<String, Object> claims, String userName) {
    	return Jwts.builder()
                .claims(claims)
                .subject(userName)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(getSignKey())
                .compact();
    }

    private SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    
    public String generateTokenWithClaims(Map<String, Object> claims, String userName) {
        return createToken(claims, userName);
    }
}
